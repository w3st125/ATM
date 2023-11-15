package org.atm.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PayInRequestParams {
    private Long userId;
    private BigDecimal amount;
}
