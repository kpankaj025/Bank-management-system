package com.bank.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.entity.TransactionRecord;

public interface TransactionRecordRepo extends JpaRepository<TransactionRecord, String> {

    @Query("Select T from TransactionRecord T where T.accountNumber=:accountNumber order by T.createdAt Desc ")
    public List<TransactionRecord> getLast5Transactions(@Param("accountNumber") String accountNumber);
}
