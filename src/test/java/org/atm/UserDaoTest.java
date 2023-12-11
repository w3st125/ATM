package org.atm;

import jakarta.transaction.Transactional;
import org.atm.db.UserDao;
import org.atm.db.model.User;
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
@ContextConfiguration(initializers = {UserDaoTest.Initializer.class})
@Testcontainers
public class UserDaoTest {

    @Autowired UserDao userDao;

    @Container
    public static JdbcDatabaseContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16.1")
                    .withDatabaseName("postgres")
                    .withUsername("postgres")
                    .withPassword("sa")
                    .withInitScript("init.sql");

    @Test
    public void should_return_user_when_given_login() {
        User user = userDao.findUserByLogin("w3st125");
        String actualLogin = user.getLogin();
        Assertions.assertEquals("w3st125", actualLogin);
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
