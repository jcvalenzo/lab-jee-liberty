package com.eibs.playground.model;

import java.math.BigDecimal;

public class Account {
    private final Long id;
    private final String accountNumber;
    private final String holderName;
    private final BigDecimal balance;

    public Account(Long id, String accountNumber, String holderName, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
