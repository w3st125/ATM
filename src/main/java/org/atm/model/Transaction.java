package org.atm.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private String accountFrom;
    private String accountTo;
    private LocalDateTime date;
    private TransactionType type;
    private Long currencyIdFrom;
    private Long currencyIdTo;
}
