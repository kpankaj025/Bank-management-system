package com.bank.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "loan")
public class LoanEntity {

    @Id
    private String id;

    private Double loanAmount;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private Double intrestRate;

    private int totalInstallment;

    private int installmentPaid;

    private Double recoveredAmount;

    private String accountNumber;

}
