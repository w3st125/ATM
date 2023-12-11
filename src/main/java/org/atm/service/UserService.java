package org.atm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atm.db.UserDao;
import org.atm.db.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDao userDao;

    public User getUserByLogin(String login) {
        log.info("UserService: get user by login {}",login);
        return userDao.findUserByLogin(login);
    }
}
