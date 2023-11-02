package org.atm.forATM;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.atm.forATM.ATM.input;

public class ATMUtils {
    private static final Pattern patternForDigit = Pattern.compile("^\\d$");
    //private static final Pattern patternForSmth = Pattern.compile("^[1-9][0-9]*$");
    private static final Pattern patternForLogin = Pattern.compile("^[a-zA-Z][a-zA-Z\\d]{5,15}$");

    public static void operationSelectionMenu() {
        System.out.println("""
                1) Показать баланс.
                2) Вывести деньги.
                3) Добавить деньги.
                4) Выход.""");

        System.out.println("\n" + "Введите номер необходимой вам операции:");
    }

    public static int inputOperationNumber() {
        String currentInput = null;
        Matcher matcher;
        boolean check = false;
        while (!check) {
            currentInput = input.nextLine();
            matcher = patternForDigit.matcher(currentInput);
            if (!matcher.find() || Integer.parseInt(currentInput) <= 0 && Integer.parseInt(currentInput) >= 5) {
                outputWrongNumberError();
            } else {
                check = true;
            }
        }
        return Integer.parseInt(currentInput);
    }

    public static String inputLogin() {
        String currentInput = null;
        Matcher matcher;
        boolean check = false;
        while (!check) {
            currentInput = input.nextLine();
            matcher = patternForLogin.matcher(currentInput);
            if (!matcher.find()) {
                outputWrongNumberError();
            } else {
                check = true;
            }
        }
        return currentInput.toLowerCase();
    }

    public static void outputWrongNumberError() {
        System.out.println("Вы допустили ошибку при вводе числа, попробуйте еще раз\n");
    }
    public static void inputWrongCredentialsError() {
        System.out.println("Введен неправильный логин или пароль, поробуйте еще раз\n");
    }
}
