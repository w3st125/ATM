package org.atm.forATM;
import org.atm.forConnect.DBUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;



public class ATM {
    static Scanner input = new Scanner(System.in);
    BigDecimal balance;
    int chosen;

    public static User doLogin() throws SQLException {
        long id;
        String pass;

        while (true) {
            System.out.println("Введите свой ID");
            id = Long.parseLong(input.nextLine());
            System.out.println("Введите свой пароль");
            pass = input.nextLine();
            User userFromTable = DBUtils.getUserFromTable(id);
            if (userFromTable == null) {
                System.out.println("Введен неправильный ID или пароль, поробуйте еще раз");
                continue;
            }
            boolean correctPass = (pass.equals(DBUtils.getUserFromTable(id).getPassword()));
            if  (!correctPass){
                System.out.println("Введен неправильный ID или пароль, поробуйте еще раз");
                continue;
            }
            break;
        }
        return DBUtils.getUserFromTable(id);
    }

    public  void outputOnDisplay(User user) throws SQLException {

        do {
            System.out.println("""
                    1) Показать баланс.
                    2) Вывести деньги.
                    3) Добавить деньги.
                    4) Выход.""");

            System.out.println("\n" + "Введите номер необходимой вам функции:");
            chosen = input.nextInt();

            switch (chosen) {
                case (1):
                    System.out.println(DBUtils.getUserFromTable(user.getId()).getBalance());
                    break;
                case (2):
                    System.out.println("Сколько вы хотите снять?");
                    BigDecimal withdraval = new BigDecimal(input.next());
                    balance = (user.getBalance()).subtract(withdraval);
                    if (balance.compareTo(BigDecimal.ZERO)<0) {
                        System.out.println("На счёте недостаточно средств");
                        break;
                    }
                        BankOperations.changeBalnce(user,balance);
                    break;
                case (3):
                    System.out.println("Сколько вы хотите положить");
                    balance = new BigDecimal(input.next()).add(user.getBalance());
                    BankOperations.changeBalnce(user,balance);
                    break;
                case (4):
                    System.out.println("затычка 4");
                    break;
                default:
                    System.out.println("Вы ввели неверное число, попробуйте еще раз.");
                    break;
            }
        }
        while (chosen != 4);
        input.close(); // вроде закрывать не надо
    }
}
