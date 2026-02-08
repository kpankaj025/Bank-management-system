package com.bank.entity;

import com.bank.entity.enums.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Account {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    private String dateOfBirth;
    private String email;
    private String gender;
    private String addharNumber;
    private String panNumber;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.SAVINGS;
    private String userName;
    private String password;
    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Embedded
    private Address address;

    private String profileImageUrl;

}
