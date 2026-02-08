package com.bank.service.seviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bank.dto.AccountDto;
import com.bank.dto.AccountTransactionDto;
import com.bank.dto.TransactionRecordDto;
import com.bank.entity.AccountTransaction;
import com.bank.exception.ResourceNotFoundException;
import com.bank.repo.AccountTransactionRepo;
import com.bank.service.AccountTransactionService;
import com.bank.service.TransactionRecordService;

@Service
public class AccountTransactionImpl implements AccountTransactionService {

    private final TransactionRecordService transactionRecordService;
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final AccountTransactionRepo accountTransactionRepo;

    public AccountTransactionImpl(TransactionRecordService transactionRecordService, RestTemplate restTemplate,
            ModelMapper modelMapper, AccountTransactionRepo accountTransactionRepo) {
        this.transactionRecordService = transactionRecordService;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        this.accountTransactionRepo = accountTransactionRepo;

    }

    String accountUrl = "lb://account-service/api/v1/accounts/";

    @Override
    public AccountTransactionDto createTransaction(AccountTransactionDto accountTransactionDto) {

        AccountTransaction accTxn = accountTransactionRepo
                .save(modelMapper.map(accountTransactionDto, AccountTransaction.class));
        AccountTransactionDto acctxnDto = modelMapper.map(accTxn, AccountTransactionDto.class);
        AccountDto acc = restTemplate.getForObject(accountUrl + accTxn.getAccountId(), AccountDto.class);
        acctxnDto.setAccountDto(acc);
        return acctxnDto;
    }

    @Override
    public AccountTransactionDto getBalanceByAccountId(String accountId) {

        AccountTransaction acctxn = accountTransactionRepo.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("no record found"));
        AccountTransactionDto acctxnDto = modelMapper.map(acctxn, AccountTransactionDto.class);
        AccountDto acc = restTemplate.getForObject(accountUrl + acctxn.getAccountId(), AccountDto.class);
        acctxnDto.setAccountDto(acc);
        return acctxnDto;
    }

    @Override
    public AccountTransactionDto withdrawmoney(String accountId, Double amount) {
        AccountTransaction acctxn = accountTransactionRepo.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("no record found"));

        if (acctxn.getBalance() < amount) {
            throw new ResourceNotFoundException("insufficient balance");
        }
        Double currentBalance = acctxn.getBalance();
        Double newBalance = currentBalance - amount;
        acctxn.setBalance(newBalance);
        AccountTransaction updatedAcctxn = accountTransactionRepo.save(acctxn);

        // saving transaction
        TransactionRecordDto dto = new TransactionRecordDto();
        dto.setAccountNumber(acctxn.getAccountNumber());
        dto.setBalance(amount);
        dto.setTxnType("debit");
        transactionRecordService.saveTransactionRecord(dto);
        AccountTransactionDto acctxnDto = modelMapper.map(updatedAcctxn, AccountTransactionDto.class);
        AccountDto acc = restTemplate.getForObject(accountUrl + updatedAcctxn.getAccountId(), AccountDto.class);
        acctxnDto.setAccountDto(acc);
        return acctxnDto;
    }

    @Override
    public AccountTransactionDto depositmoney(String accountId, Double amount) {
        AccountTransaction acctxn = accountTransactionRepo.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("no record found"));

        Double currentBalance = acctxn.getBalance();
        Double newBalance = currentBalance + amount;
        acctxn.setBalance(newBalance);
        AccountTransaction updatedAcctxn = accountTransactionRepo.save(acctxn);

        // saving transaction

        TransactionRecordDto dto = new TransactionRecordDto();
        dto.setAccountNumber(acctxn.getAccountNumber());
        dto.setBalance(amount);
        dto.setTxnType("credited");
        transactionRecordService.saveTransactionRecord(dto);

        AccountTransactionDto acctxnDto = modelMapper.map(updatedAcctxn, AccountTransactionDto.class);
        AccountDto acc = restTemplate.getForObject(accountUrl + updatedAcctxn.getAccountId(), AccountDto.class);
        acctxnDto.setAccountDto(acc);
        return acctxnDto;
    }

    @Override
    public void deleteAccountTransactionById(String transactionId) {
        if (accountTransactionRepo.existsById(transactionId)) {
            accountTransactionRepo.deleteById(transactionId);
        } else {
            throw new ResourceNotFoundException("no transaction found with id: " + transactionId);
        }
    }

    @Override
    public AccountTransactionDto getBalanceByAccountNumber(String accountNumber) {
        if (accountTransactionRepo.existsByAccountNumber(accountNumber)) {
            AccountTransaction acctxn = accountTransactionRepo.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("no record found"));
            AccountTransactionDto acctxnDto = modelMapper.map(acctxn, AccountTransactionDto.class);
            AccountDto acc = restTemplate.getForObject(accountUrl + acctxn.getAccountId(), AccountDto.class);
            acctxnDto.setAccountDto(acc);
            return acctxnDto;
        } else {
            throw new ResourceNotFoundException("no record found with account number: " + accountNumber);

        }
    }

    @Override
    public AccountTransactionDto withdrawmoneyByAccountNumber(String accountNumber, Double amount) {

        if (accountTransactionRepo.existsByAccountNumber(accountNumber)) {
            AccountTransaction acctxn = accountTransactionRepo.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("no record found"));

            if (acctxn.getBalance() < amount) {
                throw new ResourceNotFoundException("insufficient balance");
            }
            Double currentBalance = acctxn.getBalance();
            Double newBalance = currentBalance - amount;
            acctxn.setBalance(newBalance);
            AccountTransaction updatedAcctxn = accountTransactionRepo.save(acctxn);

            // save transaction

            TransactionRecordDto dto = new TransactionRecordDto();
            dto.setAccountNumber(accountNumber);
            dto.setBalance(amount);
            dto.setTxnType("debit");

            transactionRecordService.saveTransactionRecord(dto);

            AccountTransactionDto acctxnDto = modelMapper.map(updatedAcctxn, AccountTransactionDto.class);
            AccountDto acc = restTemplate.getForObject(accountUrl + updatedAcctxn.getAccountId(), AccountDto.class);
            acctxnDto.setAccountDto(acc);
            return acctxnDto;
        } else {
            throw new ResourceNotFoundException("no record found with account number: " + accountNumber);

        }
    }

    @Override
    public AccountTransactionDto depositmoneyByAccountNumber(String accountNumber, Double amount) {

        if (accountTransactionRepo.existsByAccountNumber(accountNumber)) {
            AccountTransaction acctxn = accountTransactionRepo.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("no record found"));

            Double currentBalance = acctxn.getBalance();
            Double newBalance = currentBalance + amount;
            acctxn.setBalance(newBalance);
            AccountTransaction updatedAcctxn = accountTransactionRepo.save(acctxn);

            // save transaction report

            TransactionRecordDto dto = new TransactionRecordDto();
            dto.setAccountNumber(accountNumber);
            dto.setBalance(amount);
            dto.setTxnType("credited");
            transactionRecordService.saveTransactionRecord(dto);

            AccountTransactionDto acctxnDto = modelMapper.map(updatedAcctxn, AccountTransactionDto.class);
            AccountDto acc = restTemplate.getForObject(accountUrl + updatedAcctxn.getAccountId(), AccountDto.class);
            acctxnDto.setAccountDto(acc);
            return acctxnDto;
        } else {
            throw new ResourceNotFoundException("no record found with account number: " + accountNumber);

        }
    }

    @Override
    public void deleteAccountTransactionByAccountNumber(String accountNumber) {

        if (accountTransactionRepo.existsByAccountNumber(accountNumber)) {
            accountTransactionRepo.deleteByAccountNumber(accountNumber);
        }
        throw new ResourceNotFoundException("no account present with account number: " + accountNumber);
    }

    @Override
    public boolean checkAccountTransactionByAccountNumber(String accountNumber) {

        return accountTransactionRepo.existsByAccountNumber(accountNumber);
    }

}
