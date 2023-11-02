package org.atm.forATM;

import java.time.LocalDateTime;

public class Transaction {
    private int amount;
    private Account accountFrom;
    private Account accountTo;
    private LocalDateTime date;
    private TransactionType type;
}
