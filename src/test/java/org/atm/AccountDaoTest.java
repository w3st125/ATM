package org.atm;

import jakarta.transaction.Transactional;
import java.util.List;
import org.atm.db.AccountDao;
import org.atm.db.model.Account;
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
@ContextConfiguration(initializers = {AccountDaoTest.Initializer.class})
@Testcontainers
public class AccountDaoTest {

    @Autowired AccountDao accountDao;

    @Container
    public static JdbcDatabaseContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:16.1")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("sa")
                    .withInitScript("init.sql");

    @Test
    @Transactional
    public void should_return_list_of_account_when_given_user_id() {
        List<Account> list = accountDao.findAccountByUserId(3L);
        Long actualUserId = list.get(0).getUserId();
        Assertions.assertEquals(3L, actualUserId);
    }

    @Test
    @Transactional
    public void should_return_account_when_given_number() {
        Account account = accountDao.findAccountByNumber("555");
        Long actualId = account.getAccId();
        Assertions.assertEquals(5L, actualId);
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
