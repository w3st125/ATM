package org.atm.forATM;

import org.atm.forConnect.DBUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;


public class ATM {
    static Scanner input = new Scanner(System.in);


    public static User doLogin() throws SQLException {
        User userFromTable;

        while (true) {
            System.out.println("Введите свой логин");
            String login = input.nextLine();
            System.out.println("Введите свой пароль");
            String pass = input.nextLine();
            login = login.toLowerCase();
            userFromTable = DBUtils.getUserByLogin(login);
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
        return userFromTable;
    }

    public void outputOnDisplay() throws SQLException {
        User currentUser = doLogin();
        while (true) {
            if (currentUser == null) currentUser = doLogin();
            ATMUtils.operationSelectionMenu();
            int chosen = ATMUtils.inputOperationNumber();
            switch (chosen) {
                case (1) -> showAccountBalanceByUser(currentUser);
                case (2) -> payOutMoneyToCash(currentUser);
                case (3) -> payInCashToAccount(currentUser);
                case (4) -> transactionP2P(currentUser);
                case (5) -> currentUser = null;
            }
        }
    }

    private static void transactionP2P(User currentUser) throws SQLException {
        System.out.println("На какой счёт вы хотите перевести деньги");
        String toNumber = input.nextLine(); //todo регекс для счёта
        System.out.println("Сколько вы хотите перевести");
        BigDecimal amountTransaction = ATMUtils.inputAmount();
        DBUtils.doP2P(currentUser, toNumber, amountTransaction);

    }

    private static void payInCashToAccount(User currentUser) throws SQLException {
        System.out.println("Сколько вы хотите положить");
        DBUtils.doPayInCashToAccount(currentUser);
    }

    private static void payOutMoneyToCash(User currentUser) throws SQLException {
        System.out.println("Сколько вы хотите снять?");
        DBUtils.doPayOutMoneyToCash(currentUser);

    }

    private static void showAccountBalanceByUser(User currentUser) throws SQLException {
        System.out.println(DBUtils.getAccountByUserID(currentUser.getId()).getBalance());
    }

}
