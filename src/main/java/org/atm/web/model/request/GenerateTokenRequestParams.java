package org.atm.web.model.request;

import lombok.Data;

@Data
public class GenerateTokenRequestParams {
    private String login;
    private String password;
}
