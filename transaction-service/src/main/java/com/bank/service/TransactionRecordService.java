package com.bank.service;

import java.util.List;

import com.bank.dto.TransactionRecordDto;

public interface TransactionRecordService {
    TransactionRecordDto saveTransactionRecord(TransactionRecordDto transactionRecordDto);

    List<TransactionRecordDto> getLast5Transaction(String accountNumber);
}
