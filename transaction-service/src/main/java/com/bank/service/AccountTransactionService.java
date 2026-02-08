package com.bank.service;

import com.bank.dto.AccountTransactionDto;

public interface AccountTransactionService {

    AccountTransactionDto createTransaction(AccountTransactionDto accountTransactionDto);

    AccountTransactionDto getBalanceByAccountId(String accountId);

    AccountTransactionDto getBalanceByAccountNumber(String accountNumber);

    AccountTransactionDto withdrawmoney(String accountId, Double amount);

    AccountTransactionDto withdrawmoneyByAccountNumber(String accountNumber, Double amount);

    AccountTransactionDto depositmoney(String accountId, Double amount);

    AccountTransactionDto depositmoneyByAccountNumber(String accountNumber, Double amount);

    void deleteAccountTransactionById(String transactionId);

    void deleteAccountTransactionByAccountNumber(String accountNumber);

    boolean checkAccountTransactionByAccountNumber(String accountNumber);

}
