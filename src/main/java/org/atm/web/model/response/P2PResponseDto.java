package org.atm.web.model.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class P2PResponseDto {
    String numberFrom;
    String numberTo;
    BigDecimal amountTransactionFrom;
}
