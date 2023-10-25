package org.example;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //есть идея с логином. сравнивать логин(id) с номером в arraylist, и при совпадении паролей объекту-пустышке приравнивать свойства объекта, за
        //которого хотим залогиниться. Остался вопрос реализации
        Bank.createListOfUsers();
        ATM.outputOnDisplay(ATM.logging(Bank.getUsers()));

    }
}