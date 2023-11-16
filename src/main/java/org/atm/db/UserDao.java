package org.atm.db;

import org.atm.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User,Long> {
    User findUserByLogin(String login);
}
