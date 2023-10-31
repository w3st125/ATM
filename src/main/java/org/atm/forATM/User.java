package org.atm.forATM;

import java.math.BigDecimal;
import java.util.ArrayList;

public class User{
    private long id;
    private String password;
    private BigDecimal balance; // копейки не считает, но считаем
    private ArrayList<Transaction> transactions;

    // private ArrayList<Account> accounts; На данном этапе у юзера будет только 1 аккаунт(счёт). Но оставил Список на будущее
    public User(long id, String password, BigDecimal balance) {
        this.id = id;
        this.password = password;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getPassword() { // Вряд ли ты должен уметь доставать пасс, но это решу потом
        return password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}
