package org.atm.web.model.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class  PayOutRequestParams {
    private String accountNumber;
    private BigDecimal withdrawal;
    //  private Long currencyId; // Код валюты
}
