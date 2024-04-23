package org.atm.web.model.request;

import lombok.Data;

@Data
public class HashMatcher {
    private String pass;
    private String hash;
}
