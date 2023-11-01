package org.atm.forATM;

import org.atm.forConnect.DBUtils;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BankOperations {
    public static void changeBalnce(Account account, BigDecimal toBalance) throws SQLException {
        account.setBalance(toBalance);
        DBUtils.setBalanceToTable(account.getAccId(), toBalance);
    }
}
