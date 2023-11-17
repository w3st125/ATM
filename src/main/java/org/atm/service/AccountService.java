package org.atm.service;

import lombok.RequiredArgsConstructor;
import org.atm.db.AccountDao;
import org.atm.db.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountDao accountDao;

    public List<Account> getAccountByUserId(Long userId) {

        return accountDao.findAccountByUserId(userId);
    }

    public Account getAccountByNubmer(String number) {

        return accountDao.findAccountByNumber(number);
    }
}
