package com.bank.dto;

import com.bank.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String addharNumber;
    private String panNumber;
    private String phoneNumber;
    private String accountType;
    private String userName;
    private String password;
    private Address address;
    private String accountNumber;
    private String profileImageUrl;

}
