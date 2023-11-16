package org.atm.db;

import org.atm.db.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account,Long> {
    Account findAccountByUserId(Long userId);

}
