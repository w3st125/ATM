package org.atm;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.atm.db.AccountDao;
import org.atm.db.TransactionDao;
import org.atm.model.Transaction;
import org.atm.model.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ContextConfiguration(initializers = {TransactionDaoTest.Initializer.class})
@Testcontainers
public class TransactionDaoTest {

    @Autowired TransactionDao transactionDao;
    @Autowired AccountDao accountDao;

    @Container
    public static JdbcDatabaseContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16.1")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("sa")
                    .withInitScript("init.sql");

    @Test
    public void should_change_balance_when_transaction_is_complete() { // стартовый баланс 87 и 373
        BigDecimal balanceAccountFromB4T = accountDao.findAccountByNumber("222").getBalance();
        BigDecimal balanceAccountToB4T = accountDao.findAccountByNumber("333").getBalance();
        Assertions.assertEquals(BigDecimal.valueOf(87).compareTo(balanceAccountFromB4T), 0);
        Assertions.assertEquals(BigDecimal.valueOf(373).compareTo(balanceAccountToB4T), 0);
        transactionDao.insertTransaction(
                new Transaction(
                        BigDecimal.valueOf(10),
                        "222",
                        "333",
                        LocalDateTime.now(),
                        TransactionType.P2P,
                        643L));
        BigDecimal balanceAccountFrom = accountDao.findAccountByNumber("222").getBalance();
        BigDecimal balanceAccountTo = accountDao.findAccountByNumber("333").getBalance();
        Assertions.assertEquals(BigDecimal.valueOf(77).compareTo(balanceAccountFrom), 0);
        Assertions.assertEquals(BigDecimal.valueOf(383).compareTo(balanceAccountTo), 0);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                            "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
