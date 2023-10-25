package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    static int summa;
    static int numberOfChoice;
    static Scanner input = new Scanner(System.in);

    public static User logging(ArrayList<User>users){
        int id;
        int pass;
        do {
            System.out.println("Введите свой ID");
            id = input.nextInt();
            System.out.println("Введите свой пароль");
            pass = input.nextInt();
            if ((id != users.get(id - 1).getId()) || (pass != users.get(id - 1).getPassword())) {
                System.out.println("Введен неправильный ID или пароль, поробуйте еще раз");
            }
        }while ((id != users.get(id - 1).getId()) || (pass != users.get(id - 1).getPassword()));
        return users.get(id-1);
    }
    public static void outputOnDisplay(User user){
        do {
            System.out.println("\n"+"\n"+"1) Показать баланс."+"\n"+"2) Вывести деньги." +"\n"+"3) Добавить деньги."+"\n"+"4) Выход.") ;

            System.out.println("\n"+"Введите номер необходимой вам функции:");
            numberOfChoice = input.nextInt();

            switch (numberOfChoice) {
                case (1):
                    System.out.println(user.getBalance());
                    break;
                case (2):
                    System.out.println("Сколько вы хотите снять?");
                    summa = input.nextInt();
                    if ((user.getBalance()-summa)<0){
                        System.out.println("На счёте недостаточно средств");
                    }
                    else user.setBalance(user.getBalance()-summa);
                    break;
                case (3):
                    System.out.println("Сколько вы хотите положить");
                    summa = input.nextInt();
                    user.setBalance(user.getBalance()+summa);
                    break;
                case (4):
                    System.out.println("затычка 4");
                    break;
                default:
                    System.out.println("Вы ввели неверное число, попробуйте еще раз.");
                    break;
            }
        }
        while (numberOfChoice!=4);
        input.close(); // вроде закрывать не надо
    }
}
