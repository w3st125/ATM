package org.atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.atm.db.model.User;
import org.atm.service.UserService;
import org.atm.web.service.GetTokenServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final GetTokenServiceImpl getTokenService;

    @PostMapping("/login") //получаем токен
    private String getUserByLogin(@RequestHeader String login,@RequestHeader String password) throws Exception {
        return getTokenService.getToken(login,password);
    }


}
