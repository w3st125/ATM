package org.atm.service;

import lombok.RequiredArgsConstructor;
import org.atm.db.UserDao;
import org.atm.db.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User getUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

}
