package org.example;

import org.example2.PostgreSQLConnUtils;

import java.sql.SQLException;
import java.util.Scanner;



public class ATM {
    static int summa;
    static int numberOfChoice;
    static Scanner input = new Scanner(System.in);

    public static User logging() throws SQLException {
        int id;
        int pass;

        do {
            System.out.println("Введите свой ID");
            id = input.nextInt();
            System.out.println("Введите свой пароль");
            pass = input.nextInt();
            if ((id != PostgreSQLConnUtils.getUserFromTable(id).getId() || (pass != PostgreSQLConnUtils.getUserFromTable(id).getPassword()))) {
                System.out.println("Введен неправильный ID или пароль, поробуйте еще раз");
            }
        } while ((id != PostgreSQLConnUtils.getUserFromTable(id).getId() || (pass != PostgreSQLConnUtils.getUserFromTable(id).getPassword())));
        return PostgreSQLConnUtils.getUserFromTable(id);
    }

    public static void outputOnDisplay(User user) throws SQLException {
        do {
            System.out.println("\n" + "\n" + "1) Показать баланс." + "\n" + "2) Вывести деньги." + "\n" + "3) Добавить деньги." + "\n" + "4) Выход.");

            System.out.println("\n" + "Введите номер необходимой вам функции:");
            numberOfChoice = input.nextInt();

            switch (numberOfChoice) {
                case (1):
                    System.out.println(PostgreSQLConnUtils.getUserFromTable(user.getId()).getBalance());
                    break;
                case (2):
                    System.out.println("Сколько вы хотите снять?");
                    summa = input.nextInt();
                    if ((PostgreSQLConnUtils.getUserFromTable(user.getId()).getBalance() - summa) < 0) {
                        System.out.println("На счёте недостаточно средств");
                    } else PostgreSQLConnUtils.setBalanceToTable(user.getId(),(user.getBalance()-summa));
                    break;
                case (3):
                    System.out.println("Сколько вы хотите положить");
                    summa = input.nextInt();
                    PostgreSQLConnUtils.setBalanceToTable(user.getId(),(user.getBalance()+summa));
                    break;
                case (4):
                    System.out.println("затычка 4");
                    break;
                default:
                    System.out.println("Вы ввели неверное число, попробуйте еще раз.");
                    break;
            }
        }
        while (numberOfChoice != 4);
        input.close(); // вроде закрывать не надо
    }
}
