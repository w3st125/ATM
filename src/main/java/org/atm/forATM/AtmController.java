package org.atm.forATM;

import org.atm.forConnect.DBService;
import org.atm.model.P2PRequestParams;
import org.atm.model.PayInRequestParams;
import org.atm.model.PayOutRequestParams;
import org.atm.model.User;
import org.atm.utils.ATMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;

@RestController
@RequestMapping("/operation/")
public class AtmController {
    @Autowired
    private DBService dbService;


    public  User doLogin() throws SQLException {
        User userFromTable;

        while (true) {
            System.out.println("Введите свой логин");
            String login = ATMUtils.input.nextLine();
            System.out.println("Введите свой пароль");
            String pass = ATMUtils.input.nextLine();
            login = login.toLowerCase();
            userFromTable = dbService.getUserByLogin(login);
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

//    public void outputOnDisplay() throws SQLException {
//        User currentUser = doLogin();
//        while (true) {
//            if (currentUser == null) currentUser = doLogin();
//            ATMUtils.operationSelectionMenu();
//            int chosen = ATMUtils.inputOperationNumber();
//            switch (chosen) {
//                //case (1) -> showAccountBalanceByUser(currentUser);
//                case (2) -> payOutMoneyToCash(currentUser);
//               // case (3) -> payInCashToAccount(currentUser);
//                case (4) -> transactionP2P(currentUser);
//                case (5) -> currentUser = null;
//            }
//        }
//    }
    @PostMapping("/p2p")
    private  void transactionP2P(@RequestBody P2PRequestParams p2PRequestParams) throws SQLException {
        System.out.println("На какой счёт вы хотите перевести деньги");
        System.out.println("Сколько вы хотите перевести");
        dbService.doP2P(p2PRequestParams);

    }
    @PostMapping("/pay-in")
    private  void payInCashToAccount(@RequestBody PayInRequestParams payInRequestParams) throws SQLException {
        System.out.println("Сколько вы хотите положить");
        dbService.doPayInCashToAccount(payInRequestParams);
    }
    @PostMapping("/pay-out")
    private  void payOutMoneyToCash(@RequestBody PayOutRequestParams payOutRequestParams)  throws SQLException {
        System.out.println("Сколько вы хотите снять?");
        dbService.doPayOutMoneyToCash(payOutRequestParams);

    }
    @GetMapping("/show-balance/{userId}")
    private  BigDecimal showAccountBalanceByUser(@PathVariable Long userId) throws SQLException {
        //System.out.println(dbService.getAccountByUserID(currentUser.getId()).getBalance());
        return dbService.getAccountByUserID(userId).getBalance() ;
    }

}
