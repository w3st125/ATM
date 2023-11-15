package org.atm.model;

public enum TransactionType {
    PAY_IN(1),
    PAY_OUT(2),
    P2P(3);

    private final long typeID;

    TransactionType(long typeID) {
        this.typeID = typeID;

    }

    public long getTypeID() {
        return typeID;
    }
}
