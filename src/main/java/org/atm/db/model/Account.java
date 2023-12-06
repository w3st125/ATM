package org.atm.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "acc_id")
    private long accId;

    @Column(name = "acc_user_id")
    private long userId;

    @Column(name = "acc_balance")
    private BigDecimal balance;

    @Column(name = "acc_number")
    private String number;

    @Column(name = "acc_currency_id")
    private long currencyId;

    public Account(long accId, long accUserId, BigDecimal balance, String number, Long curId) {
        this.accId = accId;
        this.userId = accUserId;
        this.balance = balance;
        this.number = number;
        this.currencyId = curId;
    }
}
