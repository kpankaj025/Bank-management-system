package com.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bank.dto.AccountTransactionDto;
import com.bank.service.AccountTransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class AccontTransactionController {

    private final AccountTransactionService accountTransactionService;

    // constructor
    public AccontTransactionController(AccountTransactionService accountTransactionService) {
        this.accountTransactionService = accountTransactionService;
    }

    @PostMapping
    public ResponseEntity<AccountTransactionDto> createAccountTransaction(
            @RequestBody AccountTransactionDto accountTransactionDto) {

        AccountTransactionDto createdTransaction = accountTransactionService.createTransaction(accountTransactionDto);

        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    // api to get balance by accountId
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountTransactionDto> getBalanceByAccountId(
            @PathVariable String accountId) {

        AccountTransactionDto accountTransactionDto = accountTransactionService.getBalanceByAccountId(accountId);

        return ResponseEntity.ok(accountTransactionDto);
    }

    // api to withdraw money and update balance
    @PostMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<AccountTransactionDto> withdrawMoney(
            @PathVariable String accountId,
            @PathVariable Double amount) {

        AccountTransactionDto updatedTransaction = accountTransactionService.withdrawmoney(accountId, amount);

        if (updatedTransaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTransaction);
    }

    // api to depositeMoney and update balance
    @PostMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<AccountTransactionDto> depositMoney(
            @PathVariable String accountId,
            @PathVariable Double amount) {

        AccountTransactionDto updatedTransaction = accountTransactionService.depositmoney(accountId, amount);

        if (updatedTransaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTransaction);
    }

    // api to delete transaction records by accountId
    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Void> deleteAccountTransactionById(
            @PathVariable String transactionId) {

        accountTransactionService.deleteAccountTransactionById(transactionId);
        return ResponseEntity.noContent().build();
    }

}
