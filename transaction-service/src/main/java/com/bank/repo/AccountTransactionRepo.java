package com.bank.repo;

import org.springframework.stereotype.Repository;

import com.bank.entity.AccountTransaction;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountTransactionRepo extends JpaRepository<AccountTransaction, String> {

    Optional<AccountTransaction> findByAccountId(String accountId);

    Optional<AccountTransaction> findByAccountNumber(String accountNumber);

    void deleteByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}
