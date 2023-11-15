package org.atm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private BigDecimal amount;
    private String accountFrom;
    private String accountTo;
    private LocalDateTime date;
    private TransactionType type;

    public Transaction(BigDecimal amount, String accountFrom, String accountTo, LocalDateTime date, TransactionType type) {
        this.amount = amount;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.date = date;
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }
}
