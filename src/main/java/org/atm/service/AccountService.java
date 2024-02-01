package org.atm.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atm.db.AccountDao;
import org.atm.db.model.Account;
import org.atm.db.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountDao accountDao;

    public List<Account> getAccountByUserId(Long userId) {

        return accountDao.findAccountByUserId(userId);
    }

    public Account getAccountByNumber(String number) {
        log.info("AccountService: get account by number {}", number);
        return accountDao.findAccountByNumber(number);
    }

    public void checkAccountBelongToUser(User user, String number){ //Принадлежность аккаунта юзеру
        if (user.getAccountList().stream().noneMatch(account -> Objects.equals(account.getNumber(), number))){
            throw new RuntimeException("Аккаунт не пренадлежит юзеру");
        }
    }
}
