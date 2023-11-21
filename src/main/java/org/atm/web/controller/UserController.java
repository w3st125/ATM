package org.atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.atm.db.model.User;
import org.atm.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{login}")
    private User getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }
}
