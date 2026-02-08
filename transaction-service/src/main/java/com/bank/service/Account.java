package com.bank.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bank.dto.AccountDto;

@FeignClient(name = "account-service")
public interface Account {

    @GetMapping("/api/v1/accounts/accountNumber/{accNo}")
    AccountDto getAccountByAccountNumber(@PathVariable("accNo") String accountNumber);

    @GetMapping("/api/v1/accounts")
    List<AccountDto> getAllAccounts();

}
