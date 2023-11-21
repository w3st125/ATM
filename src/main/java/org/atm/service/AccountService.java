package org.atm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.atm.db.AccountDao;
import org.atm.db.model.Account;
import org.springframework.stereotype.Service;

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
