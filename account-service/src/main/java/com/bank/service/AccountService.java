package com.bank.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dto.AccountDto;
import com.bank.dto.RegistrationDto;
import com.bank.entity.enums.AccountType;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(String accountId);

    AccountDto updateAccount(String accountId, AccountDto accountDto);

    void deleteAccount(String id);

    List<AccountDto> getAllAccounts();

    List<AccountDto> getAccountsByAccountType(AccountType accountType);

    AccountDto getAccountByEmail(String email);

    AccountDto getAccountByUserName(String userName);

    AccountDto getAccountByPhoneNumber(String phoneNumber);

    AccountDto getAccountByAddharNumber(String addharNumber);

    AccountDto getAccountByPanNumber(String panNumber);

    List<AccountDto> getAccountsByFirstNameAndLastName(String firstName, String lastName);

    RegistrationDto registerAccount(RegistrationDto registrationDto);

    AccountDto getAccountByAccountNumber(String accountNumber);

    AccountDto uploadProfile(String id, MultipartFile file) throws Exception;

    Resource getProfilePic(String id) throws Exception;

    Page<AccountDto> getFilteredAccount(Pageable pageable);

}
