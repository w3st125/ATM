package org.atm.db;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.atm.db.mapper.AccountMapper;
import org.atm.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountDao {
    private static final String SELECT_SQL = "select acc_id,acc_balance,acc_number from account where (acc_user_id = ?);";
    private final JdbcTemplate jdbcTemplate;


    @SneakyThrows
    public Account getAccountByUserId(Long userId) {

        Account account = jdbcTemplate.queryForObject(SELECT_SQL, new AccountMapper(), userId);
        if (account != null) {
            return account;
        } else {
            throw new IllegalStateException("У юзера обязан быть счёт");
        }

    }
}
