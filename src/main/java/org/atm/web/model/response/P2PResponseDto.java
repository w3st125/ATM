package org.atm.web.model.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class P2PResponseDto {
    String numberFrom;
    String numberTo;
    BigDecimal amountTransaction;
}
