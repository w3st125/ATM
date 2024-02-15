package org.atm.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CurrencyPair {
    private Long currencyIdFrom;
    private Long currencyIdTo;
    private BigDecimal exchangeRate;
}
