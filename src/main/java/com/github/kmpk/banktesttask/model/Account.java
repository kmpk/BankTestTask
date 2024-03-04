package com.github.kmpk.banktesttask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id")
    @OneToOne
    @MapsId
    private User user;

    @Column(name = "initial_balance")
    private BigDecimal initialBalance;

    @Column(name = "balance")
    private BigDecimal balance;

    public Account() {

    }

    public Account(BigDecimal initialBalance, BigDecimal balance) {
        this.initialBalance = initialBalance;
        this.balance = balance;
    }
}
