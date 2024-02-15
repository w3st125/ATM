package org.atm.db;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.atm.model.CurrencyPair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CurrencyPairDao {
    private static final String SQL_UPDATE_CURRENCY_PAIR =
            "update currency_pair set cer_exchange_rate= ? where cer_currency_id_from = ? and"
                    + " cer_currency_id_to = ?";
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public void updateColumnCurrencyPair(CurrencyPair currencyPair) {
        jdbcTemplate.update(
                SQL_UPDATE_CURRENCY_PAIR,
                currencyPair.getExchangeRate(),
                currencyPair.getCurrencyIdFrom(),
                currencyPair.getCurrencyIdTo());
    }

    public BigDecimal findExchangeRateById(Long idFrom, Long idTo) {
        String SQL_FIND_CURRENCY_PAIR_RATE =
                "select cer_exchange_rate from currency_pair  where cer_currency_id_from =? and"
                        + " cer_currency_id_to =?";
        return jdbcTemplate.queryForObject(
                SQL_FIND_CURRENCY_PAIR_RATE, BigDecimal.class, idFrom, idTo);
    }
}
