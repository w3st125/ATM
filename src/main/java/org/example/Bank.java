package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

    public static ArrayList<User> getUsers() {
        return users;
    }

    private static ArrayList<User>users = new ArrayList<>();
    public static void createListOfUsers() throws FileNotFoundException {
        File file = new File("src/main/resources/data.txt");
        Scanner sc = new Scanner(file);
        sc.useDelimiter("\n");
        while(sc.hasNext()){  //парсим строку в объект User
            users.add(parseCSVLine(sc.next()));
        }
    }

    private static User parseCSVLine(String line) {
        String[] data = line.split(",");
        int[] numbers = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            numbers[i] = Integer.parseInt(data[i]);
        }
        int id = numbers[0];
        int pass = numbers[1];
        int balance = numbers[2];
        User user = new User(id,pass,balance);
        return user;
    }
    // private ArrayList<Account>accounts;
}

