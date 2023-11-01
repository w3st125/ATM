package org.atm.forATM;
import org.atm.forConnect.DBUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;



public class ATM {
    static Scanner input = new Scanner(System.in);
    BigDecimal balance;
    int chosen;

    public static Account doLogin() throws SQLException {
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
            boolean correctPass = (pass.equals(userFromTable.getPassword()));
            if  (!correctPass){
                System.out.println("Введен неправильный ID или пароль, поробуйте еще раз");
                continue;
            }
            break;
        }
        return DBUtils.getAccountFromTable(id);
    }

    public  void outputOnDisplay(Account account) throws SQLException {

        do {
            ATMUtils.functionSelectionMenu();
            balance = account.getBalance();
            chosen = ATMUtils.inputNumber();

            switch (chosen) {
                case (1):
                    System.out.println(balance);
                    break;
                case (2):
                    System.out.println("Сколько вы хотите снять?");
                    BigDecimal withdrawal = new BigDecimal(input.next());
                    balance=balance.subtract(withdrawal);
                    if (balance.compareTo(BigDecimal.ZERO)<0) {
                        System.out.println("На счёте недостаточно средств");
                        break;
                    }
                        BankOperations.changeBalnce(account,balance);
                    break;
                case (3):
                    System.out.println("Сколько вы хотите положить");
                    balance = new BigDecimal(input.next()).add(balance);
                    BankOperations.changeBalnce(account,balance);
                    break;
                case (4):
                    System.out.println("затычка 4");
                    break;
            }
        }
        while (chosen != 4);
        input.close(); // вроде закрывать не надо
    }
}
