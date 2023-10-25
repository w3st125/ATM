package org.example;
import java.util.ArrayList;

public class User implements Cloneable {
    private int id;
    private int password;
    private int balance; // копейки не считает, но считаем
    private ArrayList<Transaction>transactions;
    // private ArrayList<Account> accounts; На данном этапе у юзера будет только 1 аккаунт(счёт). Но оставил Список на будущее
    public User(int id, int password,int balance){
        this.id=id;
        this.password=password;
        this.balance=balance;
    }

    public int getId() {
        return id;
    }
    public int getBalance() {
        return balance;
    }
    public int getPassword() { // Вряд ли ты должен уметь доставать пасс, но это решу потом
        return password;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public User clone() {
        try {
            User clone = (User) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
