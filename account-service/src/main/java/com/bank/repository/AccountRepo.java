package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Account;
import com.bank.entity.enums.AccountType;

public interface AccountRepo extends JpaRepository<Account, String> {

    List<Account> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUserName(String userName);

    Optional<Account> findByPhoneNumber(String phoneNumber);

    Optional<Account> findByAddharNumber(String addharNumber);

    Optional<Account> findByPanNumber(String panNumber);

    List<Account> findByAccountType(AccountType accountType);

    Account findByAccountNumber(String accountNumber);
}
