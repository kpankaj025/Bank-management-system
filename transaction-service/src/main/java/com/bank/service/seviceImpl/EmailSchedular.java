package com.bank.service.seviceImpl;

import java.util.List;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bank.dto.AccountDto;
import com.bank.dto.EmailDto;
import com.bank.entity.TransactionRecord;
import com.bank.repo.TransactionRecordRepo;
import com.bank.service.Account;

@Component
public class EmailSchedular {

    private TransactionRecordRepo trp;
    private StreamBridge streamBridge;
    private Account account;

    public EmailSchedular(TransactionRecordRepo trp, StreamBridge streamBridge, Account account) {
        this.trp = trp;
        this.streamBridge = streamBridge;
        this.account = account;
    }

    @Scheduled(cron = "0 40 0 * * ?")
    public void sendEmailNotifications() {
        System.out.println("sending daily message..........");

        List<AccountDto> accounts = account.getAllAccounts();
        for (AccountDto acc : accounts) {
            EmailDto emailDto = new EmailDto();
            emailDto.setTo(acc.getEmail());
            // get last 5 transactions for the account
            List<TransactionRecord> transactions = trp.getLast5Transactions(acc.getAccountNumber());
            StringBuilder emailBody = new StringBuilder("Your last 5 transactions:\n");
            // add transaction details to email body
            for (TransactionRecord tr : transactions) {
                emailBody.append("Date: ").append(tr.getCreatedAt())
                        .append(", Amount: ").append(tr.getBalance())
                        .append(", Type: ").append(tr.getTxnType())
                        .append("\n");
            }
            emailDto.setSubject("Daily Transaction Summary");
            emailDto.setBody(emailBody.toString());

            Message<EmailDto> message = MessageBuilder.withPayload(emailDto).build();

            streamBridge.send("dailyEmail-out-0", message);
        }
    }

}
