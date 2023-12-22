package org.atm.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CurrencyPair {
    private Long currencyIdFrom;
    private Long currencyIdTo;
    private BigDecimal exchangeRate;
}
