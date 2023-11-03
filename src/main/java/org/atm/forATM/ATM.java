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
        User userFromTable;

        while (true) {
            System.out.println("Введите свой логин");
            String login = ATMUtils.inputLogin();
            System.out.println("Введите свой пароль");
            String pass = ATMUtils.inputPassword();
            userFromTable = DBUtils.getUserFromTable(login);
            if (userFromTable == null) {
                ATMUtils.inputWrongCredentialsError();
                continue;
            }
            boolean correctPass = (pass.equals(userFromTable.getPassword()));
            if (!correctPass) {
                ATMUtils.inputWrongCredentialsError();
                continue;
            }
            break;
        }
        return DBUtils.getAccountFromTable(userFromTable.getId());
    }

    public void outputOnDisplay(Account account) throws SQLException {

        do {
            ATMUtils.operationSelectionMenu();
            balance = account.getBalance();
            chosen = ATMUtils.inputOperationNumber();

            switch (chosen) {
                case (1):
                    System.out.println(balance);
                    break;
                case (2):
                    System.out.println("Сколько вы хотите снять?");
                    BigDecimal withdrawal = ATMUtils.inputAmount();
                    balance = balance.subtract(withdrawal);
                    if (balance.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.println("На счёте недостаточно средств");
                        break;
                    }
                    BankOperations.changeBalnce(account, balance);
                    break;
                case (3):
                    System.out.println("Сколько вы хотите положить");
                    balance =  ATMUtils.inputAmount().add(balance);
                    BankOperations.changeBalnce(account, balance);
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
