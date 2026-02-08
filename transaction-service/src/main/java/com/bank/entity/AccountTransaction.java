package com.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {

    @Id
    private String id;

    @Column(nullable = false)
    private String accountId;
    @Column(nullable = false)
    private String accountNumber;
    private Double balance;

}
