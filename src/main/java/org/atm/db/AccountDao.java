package org.atm.db;

import org.atm.db.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDao extends JpaRepository<Account, Long> {
    List<Account> findAccountByUserId(Long userId);

    Account findAccountByNumber(String number);

}
