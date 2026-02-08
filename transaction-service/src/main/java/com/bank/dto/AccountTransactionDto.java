package com.bank.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionDto {

    private String accountId;
    private String accountNumber;
    private Double balance;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AccountDto accountDto;
}
