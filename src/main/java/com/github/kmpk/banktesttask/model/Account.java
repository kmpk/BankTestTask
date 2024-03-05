package com.github.kmpk.banktesttask.model;

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
    private Integer id;

    @JoinColumn(name = "id")
    @OneToOne
    @MapsId
    private User user;

    private BigDecimal initialBalance;

    private BigDecimal balance;

    public Account() {

    }

    public Account(BigDecimal initialBalance, BigDecimal balance) {
        this.initialBalance = initialBalance;
        this.balance = balance;
    }
}
