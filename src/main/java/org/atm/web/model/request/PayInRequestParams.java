package org.atm.web.model.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PayInRequestParams {
    private String accountNumber;
    private BigDecimal amount;
    //  private  Long currencyId; // Код валюты
}
