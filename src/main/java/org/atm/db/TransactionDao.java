package org.atm.db;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.atm.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionDao {
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public void insertTransaction(Transaction transaction) {
        jdbcTemplate.execute("insert into transaction (txn_account_from, txn_account_to, txn_date, txn_type_id, txn_amount) values" +
                " ('" + transaction.getAccountFrom() + "','" + transaction.getAccountTo() + "','" + transaction.getDate() + "','" + transaction.getType().getTypeId() + "','" + transaction.getAmount() + "');");
    }
}
