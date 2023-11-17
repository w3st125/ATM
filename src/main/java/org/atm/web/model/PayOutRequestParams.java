package org.atm.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayOutRequestParams {
    private String accountNumber;
    private BigDecimal withdrawal;
//  private Long currencyId; // Код валюты
}
