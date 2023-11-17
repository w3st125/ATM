package org.atm.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayInRequestParams {
    private String accountNumber;
    private BigDecimal amount;
//  private  Long currencyId; // Код валюты
}
