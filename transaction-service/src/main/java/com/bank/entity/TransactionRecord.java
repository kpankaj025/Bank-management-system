package com.bank.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecord {

    @Id
    private String id;

    private String accountNumber;

    @CreationTimestamp
    private LocalDate createdAt;
    private Double balance;
    private String txnType;

    @UpdateTimestamp
    private LocalDate updatedAt;

}
