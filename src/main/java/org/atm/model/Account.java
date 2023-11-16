package org.atm.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private long userId;
    private long accId;
    private BigDecimal balance;
    private String number;

    public Account(long accId, BigDecimal balance, String number) {
        this.accId = accId;
        this.balance = balance;
        this.number = number;
    }
}

