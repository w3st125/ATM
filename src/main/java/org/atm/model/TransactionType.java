package org.atm.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    PAY_IN(1),
    PAY_OUT(2),
    P2P(3);

    private final long typeId;
}
