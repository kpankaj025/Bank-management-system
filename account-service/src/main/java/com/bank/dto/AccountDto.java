package com.bank.dto;

import java.time.LocalDate;

import com.bank.entity.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String addharNumber;
    private String panNumber;
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String accountNumber;

    private Address address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String profileImageUrl;

}
