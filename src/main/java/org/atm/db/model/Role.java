package org.atm.db.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(1),
    USER(2);

    private final int roleId;

    public static Role getRole(int id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getRoleId() == id)
                        .findFirst().orElseThrow(RuntimeException::new);
    }
}
