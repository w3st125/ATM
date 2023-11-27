package org.atm;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.atm.db.UserDao;
import org.atm.db.model.Account;
import org.atm.db.model.User;
import org.atm.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
/*@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)*/

public class UserServiceTest {
    @MockBean private UserDao userDao;

    @Autowired private UserService userService;

    private static String login;
    private static List<Account> accountList = new ArrayList<>();
    private static User testUser;

    @BeforeAll
    public static void prepareTestData() {
        Account testAccount = new Account(1L, BigDecimal.valueOf(10), "222", 638L);
        login = "w3st125";
        accountList.add(testAccount);
        testUser = new User(1, "2", "w3st125", accountList);
    }

    @Test
    public void should_return_user_when_given_login() { // Лучше заренеймить в виде формата
        when(userDao.findUserByLogin(argThat(arg -> Objects.equals(arg, testUser.getLogin()))))
                .thenReturn(testUser);

        User user2 = userService.getUserByLogin(login);

        assertEquals(testUser, user2);
    }
}
