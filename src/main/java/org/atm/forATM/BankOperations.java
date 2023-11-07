package org.atm.forATM;

import org.atm.forConnect.DBUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class BankOperations {
    public static void changeBalanceByAccount(Account account, BigDecimal toBalance, Connection connection) throws SQLException {
        account.setBalance(toBalance);
        DBUtils.setBalanceByAccountID(account.getAccId(), toBalance, connection);
    }
}