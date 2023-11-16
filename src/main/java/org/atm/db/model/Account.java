package org.atm.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
public class Account {

    @Id
    @Column(name ="acc_id")
    private long accId;
    @Column(name ="acc_user_id")
    private long userId;
    @Column(name ="acc_balance")
    private BigDecimal balance;
    @Column(name ="acc_number")
    private String number;

    public Account(long accId, BigDecimal balance, String number) {
        this.accId = accId;
        this.balance = balance;
        this.number = number;
    }
}

