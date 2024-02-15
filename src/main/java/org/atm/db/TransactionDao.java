package org.atm.db;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.atm.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionDao {
    private static final String SQL_INSERT_INTO_TRANSACTION =
            "insert into transaction (txn_account_from, txn_account_to, txn_date, txn_type_id,"
                + " txn_amount_from,txn_amount_to, txn_currency_id_from,txn_currency_id_to) values"
                + " (?,?,?,?,?,?,?,?)";
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public void insertTransaction(Transaction transaction) {
        jdbcTemplate.update(
                SQL_INSERT_INTO_TRANSACTION,
                transaction.getAccountFrom(),
                transaction.getAccountTo(),
                transaction.getDate(),
                transaction.getType().getTypeId(),
                transaction.getAmountFrom(),
                transaction.getAmountTo(),
                transaction.getCurrencyIdFrom(),
                transaction.getCurrencyIdTo());
    }
}
