package org.atm.web.model.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PayOutResponseDto {
    String number;
    BigDecimal withdrawal;
}
