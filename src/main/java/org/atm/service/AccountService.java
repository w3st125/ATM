package org.atm.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atm.db.AccountDao;
import org.atm.db.model.Account;
import org.atm.db.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountDao accountDao;

    public List<Account> getAccountByUserId(Long userId) {

        return accountDao.findAccountListByUserId(userId);
    }

    public Account getAccountByNumber(String number) {
        log.info("AccountService: get account by number {}", number);
        return accountDao.findAccountByNumber(number);
    }

    public void checkAccountBelongToUserByAccountNumber(
            User user, String number) { // Принадлежность аккаунта юзеру по номеру аккаунта
        if (accountDao.findAccountListByUserId(user.getId()).stream()
                .noneMatch(account -> Objects.equals(account.getNumber(), number))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Аккаунт не принадлежит юзеру");
        }
    }

    public void checkAccountBelongToUserByLogin(
            User user, String login) { // Принадлежность аккаунта юзеру по логину
        if (!user.getLogin().equals(login)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Аккаунт не принадлежит юзеру");
        }
    }
}
