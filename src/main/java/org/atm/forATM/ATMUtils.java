package org.atm.forATM;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.atm.forATM.ATM.input;

public class ATMUtils {
    private static final Pattern patternForDigit = Pattern.compile("\\d");

    public static void functionSelectionMenu() {
        System.out.println("""
                1) Показать баланс.
                2) Вывести деньги.
                3) Добавить деньги.
                4) Выход.""");

        System.out.println("\n" + "Введите номер необходимой вам функции:");
    }

    public static int inputNumber() {
        String currentInput;
        Matcher matcher;
        boolean check = false;
        do {
            currentInput = input.nextLine();
            matcher = patternForDigit.matcher(currentInput);
            if (!matcher.find() || Integer.parseInt(currentInput) <= 0 && Integer.parseInt(currentInput) >= 5) {
                outputWrongNumberEror();
            } else {
                check = true;
            }
        }
        while (!check);
        return Integer.parseInt(currentInput);
    }


    public static void outputWrongNumberEror() {
        System.out.println("Вы допустили ошибку при вводе числа, попробуйте еще раз\n");
    }

}
