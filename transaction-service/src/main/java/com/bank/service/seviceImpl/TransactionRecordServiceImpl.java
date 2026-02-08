package com.bank.service.seviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bank.dto.TransactionRecordDto;
import com.bank.entity.TransactionRecord;
import com.bank.repo.TransactionRecordRepo;
import com.bank.service.TransactionRecordService;
import com.bank.util.Helper;

@Service
public class TransactionRecordServiceImpl implements TransactionRecordService {

    private final TransactionRecordRepo transactionRecordRepo;
    private final ModelMapper modelMapper;

    public TransactionRecordServiceImpl(TransactionRecordRepo transactionRecordRepo, ModelMapper modelMapper) {
        this.transactionRecordRepo = transactionRecordRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionRecordDto saveTransactionRecord(TransactionRecordDto transactionRecordDto) {

        TransactionRecord tr = new TransactionRecord();
        tr.setId(Helper.idGenerator());
        tr.setAccountNumber(transactionRecordDto.getAccountNumber());
        tr.setTxnType(transactionRecordDto.getTxnType());
        tr.setBalance(transactionRecordDto.getBalance());

        TransactionRecord savedTxn = transactionRecordRepo.save(tr);

        return modelMapper.map(savedTxn, TransactionRecordDto.class);

    }

    @Override
    public List<TransactionRecordDto> getLast5Transaction(String accountNumber) {

        List<TransactionRecord> list = transactionRecordRepo.getLast5Transactions(accountNumber);

        return list.stream().map(rec -> modelMapper.map(rec, TransactionRecordDto.class)).toList();

    }

}
