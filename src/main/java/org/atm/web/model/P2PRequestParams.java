package org.atm.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class P2PRequestParams {
    private String accountNumberFrom; //От кого
    private String accountNumberTo; //Кому
    private BigDecimal amount; //Сколько
//  private Long currencyId; // Код валюты

}
