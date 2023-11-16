package org.atm.utils;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATMUtils {
    private static final Pattern patternForDigit = Pattern.compile("^\\d$");
    private static final Pattern patternForLogin = Pattern.compile("^[a-zA-Z][a-zA-Z\\d]{5,15}$");
    private static final Pattern patternForPassword = Pattern.compile("^[\\w]{1,32}$"); //todo сделать от 6 символов(когда-нибудь)
    private static final Pattern patternForAmount = Pattern.compile("^\\d{1,18}(?:[.]\\d{2})?$"); //todo узнать что за символ :
    public static Scanner input = new Scanner(System.in);

    public static void operationSelectionMenu() {
        System.out.println("""
                1) Показать баланс.
                2) Вывести деньги.
                3) Добавить деньги.
                4) Перевод денег на другой счёт.
                5) Выход.""");

        System.out.println("\n" + "Введите номер необходимой вам операции:");
    }

    public static int inputOperationNumber() {
        String currentInput = null;
        Matcher matcher;
        boolean check = false;
        while (!check) {
            currentInput = input.nextLine();
            matcher = patternForDigit.matcher(currentInput);
            if (!matcher.find() || Integer.parseInt(currentInput) <= 0 && Integer.parseInt(currentInput) >= 6) {
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
                outputWrongFormatLogin();
                continue;
            } else check = true;
        }
        return currentInput.toLowerCase();
    }

    public static String inputPassword() {
        String currentInput = null;
        Matcher matcher;
        boolean check = false;
        while (!check) {
            currentInput = input.nextLine();
            matcher = patternForPassword.matcher(currentInput);
            if (!matcher.find()) {
                outputWrongFormatPassword();
                continue;
            } else check = true;
        }
        return currentInput;
    }

    public static BigDecimal inputAmount() {
        String currentInput = null;
        Matcher matcher;
        boolean check = false;
        while (!check) {
            currentInput = input.nextLine();
            matcher = patternForAmount.matcher(currentInput);
            if (!matcher.find()) {
                outputWrongNumberError();
                continue;
            } else check = true;
        }
        return new BigDecimal(currentInput);
    }

    public static void outputWrongNumberError() {
        System.out.println("Вы допустили ошибку при вводе числа, попробуйте еще раз\n");
    }

    public static void inputWrongCredentialsError() {
        System.out.println("Введен неправильный логин или пароль, поробуйте еще раз\n");
    }

    public static void outputWrongFormatLogin() {
        System.out.println("Неверный формат логина, попробуйте еще раз\n");
    }

    public static void outputWrongFormatPassword() {
        System.out.println("Неверный формат пароля, попробуйте еще раз\n");
    }
}
