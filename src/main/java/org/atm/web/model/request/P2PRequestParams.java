package org.atm.web.model.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class P2PRequestParams {
    private String accountNumberFrom; // От кого
    private String accountNumberTo; // Кому
    private BigDecimal amount; // Сколько
    //  private Long currencyId; // Код валюты

}
