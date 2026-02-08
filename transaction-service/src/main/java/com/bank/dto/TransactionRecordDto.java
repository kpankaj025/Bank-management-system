package com.bank.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecordDto {

    private String id;

    private String accountNumber;
    private LocalDate createdAt;
    private Double balance;
    private String txnType;

}
