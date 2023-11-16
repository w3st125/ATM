package org.atm.service;

import lombok.RequiredArgsConstructor;
import org.atm.db.AccountDao;
import org.atm.model.Account;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountDao accountDao;

    public Account getAccountByUserId(Long userId) {
        return accountDao.getAccountByUserId(userId);
    }
}
