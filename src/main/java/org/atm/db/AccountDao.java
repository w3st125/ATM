package org.atm.db;

import java.util.List;
import org.atm.db.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, Long> {
    List<Account> findAccountListByUserId(Long userId);

    Account findAccountByNumber(String number);
}
