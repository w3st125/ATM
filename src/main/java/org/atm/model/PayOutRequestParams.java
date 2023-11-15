package org.atm.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayOutRequestParams {
    private Long userId;
    private BigDecimal withdrawal;
}
