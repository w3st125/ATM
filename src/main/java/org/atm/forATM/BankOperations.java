package org.atm.forATM;

import org.atm.forConnect.DBUtils;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BankOperations {
    public static void changeBalnce(User user, BigDecimal toBalance) throws SQLException {
        DBUtils.setBalanceToTable(user.getId(), toBalance);
        user.setBalance(toBalance);
    }
}
