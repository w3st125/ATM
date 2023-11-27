package org.atm.web.model.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PayOutResponseDto {
    String number;
    BigDecimal withdrawal;
}
