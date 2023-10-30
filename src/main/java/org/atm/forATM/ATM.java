package org.atm.forATM;
import org.atm.forConnect.PostgreSQLConnUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;



public class ATM {
    static BigDecimal summa;
    static int numberOfChoice;
    static Scanner input = new Scanner(System.in);

    public static User logging() throws SQLException {
        long id;
        String pass;

        do {
            System.out.println("Введите свой ID");
            id = Long.parseLong(input.nextLine());
            System.out.println("Введите свой пароль");
            pass = input.nextLine();
            if ((id != PostgreSQLConnUtils.getUserFromTable(id).getId() || (!Objects.equals(pass, PostgreSQLConnUtils.getUserFromTable(id).getPassword())))) {
                System.out.println("Введен неправильный ID или пароль, поробуйте еще раз");
            }
        } while ((id != PostgreSQLConnUtils.getUserFromTable(id).getId() || (!Objects.equals(pass, PostgreSQLConnUtils.getUserFromTable(id).getPassword()))));
        return PostgreSQLConnUtils.getUserFromTable(id);
    }

    public static void outputOnDisplay(User user) throws SQLException {

        do {
            System.out.println("""
                    1) Показать баланс.
                    2) Вывести деньги.
                    3) Добавить деньги.
                    4) Выход.""");

            System.out.println("\n" + "Введите номер необходимой вам функции:");
            numberOfChoice = input.nextInt();

            switch (numberOfChoice) {
                case (1):
                    System.out.println(PostgreSQLConnUtils.getUserFromTable(user.getId()).getBalance());
                    break;
                case (2):
                    System.out.println("Сколько вы хотите снять?");
                    summa = new BigDecimal(input.next());
                    if ((PostgreSQLConnUtils.getUserFromTable(user.getId()).getBalance().subtract(summa)).compareTo(BigDecimal.ZERO) < 0) {
                        System.out.println("На счёте недостаточно средств");
                    } else {
                        PostgreSQLConnUtils.setBalanceToTable(user.getId(), (user.getBalance().subtract(summa)));
                        user.setBalance(user.getBalance().subtract(summa));
                    }
                    break;
                case (3):
                    System.out.println("Сколько вы хотите положить");
                    summa = new BigDecimal(input.next());
                    PostgreSQLConnUtils.setBalanceToTable(user.getId(),(user.getBalance().add(summa)));
                    user.setBalance(user.getBalance().add(summa));
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
