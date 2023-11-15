package org.atm.model;

public class User {
    private long id;
    private String password;

    private String login;

    public User(long id, String password, String login) {
        this.id = id;
        this.password = password;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public String getPassword() { // Вряд ли ты должен уметь доставать пасс, но это решу потом
        return password;
    }

    public String getLogin() {
        return login;
    }


}
