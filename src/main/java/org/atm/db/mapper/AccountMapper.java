package org.atm.db.mapper;

import org.atm.db.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountMapper implements RowMapper<Account> {


    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

        long accountId = rs.getLong(1);
        BigDecimal accountBalance = rs.getBigDecimal(2);
        String accountNumber = rs.getString(3);
        Long currencyId = rs.getLong(4);

        return new Account(accountId, accountBalance, accountNumber, currencyId);
    }
}
