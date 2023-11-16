package org.atm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Transaction {
    private BigDecimal amount;
    private String accountFrom;
    private String accountTo;
    private LocalDateTime date;
    private TransactionType type;
}
