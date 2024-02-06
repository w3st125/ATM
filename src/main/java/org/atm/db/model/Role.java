package org.atm.db.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(1),USER(2);

    private final int roleId;
}
