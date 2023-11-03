package org.atm.forATM;

import java.math.BigDecimal;

public class Account {

   private long userId;
   private long accId;
   private BigDecimal balance;

    private String number;

    public Account(long userId, long accId, BigDecimal balance, String number) {
        this.userId = userId;
        this.accId = accId;
        this.balance = balance;
        this.number = number;
    }


    public long getAccId() {
        return accId;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

}

