package org.atm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    private BigDecimal amount;
    private String accountFrom;
    private String accountTo;
    private LocalDateTime date;
    private TransactionType type;
    private Long curId;
}
