package org.atm.utils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.atm.integration.model.Currency;
import org.atm.model.CurrencyPair;

public class ATMUtils {
    private static final Pattern patternForDigit = Pattern.compile("^\\d$");
    private static final Pattern patternForLogin = Pattern.compile("^[a-zA-Z][a-zA-Z\\d]{5,15}$");
    private static final Pattern patternForPassword =
            Pattern.compile("^[\\w]{1,32}$"); // todo сделать от 6 символов(когда-нибудь)
    private static final Pattern patternForAmount =
            Pattern.compile("^\\d{1,18}(?:[.]\\d{2})?$"); // todo узнать что за символ :
    public static Scanner input = new Scanner(System.in);

    public static void operationSelectionMenu() {
        System.out.println(
                """
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
            if (!matcher.find()
                    || Integer.parseInt(currentInput) <= 0 && Integer.parseInt(currentInput) >= 6) {
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

    public static List<CurrencyPair> createListOfPair(List<Currency> listOfCur) {
        List<CurrencyPair> list = new ArrayList<>();
        for (int i = 0; i < listOfCur.size(); i++) {
            Currency currency;
            CurrencyPair currencyPair = new CurrencyPair();
            currencyPair.setCurrencyIdFrom(Long.valueOf(listOfCur.get(0).getCode()));
            currencyPair.setCurrencyIdTo(Long.valueOf(listOfCur.get(1).getCode()));
            currencyPair.setExchangeRate(
                    BigDecimal.valueOf(Double.parseDouble(listOfCur.get(0).getRate()))
                            .divide(
                                    (BigDecimal.valueOf(
                                            Double.parseDouble(listOfCur.get(1).getRate()))),
                                    4,
                                    BigDecimal.ROUND_DOWN));
            CurrencyPair currencyPair1 = new CurrencyPair();
            currencyPair1.setCurrencyIdFrom(Long.valueOf(listOfCur.get(1).getCode()));
            currencyPair1.setCurrencyIdTo(Long.valueOf(listOfCur.get(0).getCode()));
            currencyPair1.setExchangeRate(
                    BigDecimal.valueOf(Double.parseDouble(listOfCur.get(1).getRate()))
                            .divide(
                                    (BigDecimal.valueOf(
                                            Double.parseDouble(listOfCur.get(0).getRate()))),
                                    4,
                                    BigDecimal.ROUND_DOWN));
            list.add(currencyPair);
            list.add(currencyPair1);
            currency = listOfCur.get(0);
            listOfCur.remove(0);
            listOfCur.add(currency);
        }
        for (CurrencyPair currencyPair : list) {
            System.out.println(currencyPair);
        }
        return list;
    }

    public static List<CurrencyPair> createListPair(List<Currency> listOfCurrency) {
        List<CurrencyPair> listOfPair = new ArrayList<>();
        for (int i = 0; i < listOfCurrency.size(); i++) {
            for (int y = 0; y < listOfCurrency.size(); y++) {
                CurrencyPair currencyPair = new CurrencyPair();
                Currency cur1 = listOfCurrency.get(i);
                Currency cur2 = listOfCurrency.get(y);
                currencyPair.setCurrencyIdFrom(Long.valueOf(cur1.getCode()));
                currencyPair.setCurrencyIdTo(Long.valueOf(cur2.getCode()));
                currencyPair.setExchangeRate(
                        (BigDecimal.valueOf(Double.parseDouble(cur1.getRate())))
                                .divide(
                                        (BigDecimal.valueOf(Double.parseDouble(cur2.getRate()))),
                                        4,
                                        BigDecimal.ROUND_DOWN));
                System.out.println(currencyPair);
                listOfPair.add(currencyPair);
            }
        }
        return listOfPair;
    }
}
