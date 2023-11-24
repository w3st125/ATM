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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock private UserDao userDao;

    private UserService userService;

    private static String login;
    private static List<Account> accountList = new ArrayList<>();
    private static User testUser;

    @BeforeClass
    public static void prepareTestData() {
        Account testAccount = new Account(1L, BigDecimal.valueOf(10), "222", 638L);
        login = "w3st125";
        accountList.add(testAccount);
        testUser = new User(1, "2", "w3st125", accountList);
    }

    @Before
    public void init() {
        userService = new UserService(userDao);
    }

    @Test
    public void userTest() { // Лучше заренеймить в виде формата Given_When_Then
        when(userDao.findUserByLogin(argThat(arg -> Objects.equals(arg, testUser.getLogin()))))
                .thenReturn(testUser);

        User user2 = userService.getUserByLogin(login);

        assertEquals(testUser, user2);
    }
}
