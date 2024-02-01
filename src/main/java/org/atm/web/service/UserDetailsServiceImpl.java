package org.atm.web.service;

import lombok.RequiredArgsConstructor;
import org.atm.db.UserDao;
import org.atm.db.model.User;
import org.atm.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException { // Username = login
        User user = userService.getUserByLogin(login);
        if (user == null){
            throw new UsernameNotFoundException(login);
        }
        return user;
    }
}
