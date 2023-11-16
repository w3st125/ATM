package org.atm.db;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.atm.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class UserDao {
    private static final String SELECT_SQL = "select user_id,user_pass,user_login from bank_user where (user_login = ?);";
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public User getUserByLogin(String login) {
        return jdbcTemplate.queryForObject(SELECT_SQL,
                (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2), rs.getString(3)), login);

    }
}