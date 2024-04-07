package org.atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.atm.web.model.request.GenerateTokenRequestParams;
import org.atm.web.service.GetTokenServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final GetTokenServiceImpl getTokenService;

    @PostMapping("/login") // получаем токен
    public String generateToken(@RequestBody GenerateTokenRequestParams generateTokenRequestParams)
            throws Exception {
        return getTokenService.getToken(
                generateTokenRequestParams.getLogin(), generateTokenRequestParams.getPassword());
    }
}
