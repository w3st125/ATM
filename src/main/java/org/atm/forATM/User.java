package org.atm.forATM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User{
    private long id;
    private String password;
    public User(long id, String password) {
        this.id = id;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getPassword() { // Вряд ли ты должен уметь доставать пасс, но это решу потом
        return password;
    }



}
