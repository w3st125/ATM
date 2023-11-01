package org.atm.forATM;

import java.math.BigDecimal;

public class Account {

   private long userId;
   private long accId;// holder - владелец.
   private BigDecimal balance;

   public Account(long userId, long accId, BigDecimal balance ){
        this.userId=userId;
        this.accId=accId;
        this.balance=balance;
        //this.transactions= new ArrayList<Transaction>();
    }

    public long getAccId() {
        return accId;
    }

    public long getUserId() {
        return accId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}

