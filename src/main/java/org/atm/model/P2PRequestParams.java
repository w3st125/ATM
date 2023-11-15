package org.atm.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class P2PRequestParams {
    private Long userId; //От кого
    private String number; //Кому
    private BigDecimal amount; //Сколько

}
