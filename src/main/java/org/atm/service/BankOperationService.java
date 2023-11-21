package org.atm.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.atm.db.TransactionDao;
import org.atm.db.model.Account;
import org.atm.model.Transaction;
import org.atm.model.TransactionType;
import org.atm.utils.CommonExceptionHandler;
import org.atm.utils.CurrencyException;
import org.atm.utils.InsufficientFundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankOperationService {

    private final TransactionDao transactionDao;
    private final AccountService accountService;

    public void doP2P(String numberFrom, String numberTo, BigDecimal amountTransaction) {
        Account accountFrom = accountService.getAccountByNubmer(numberFrom);
        Account accountTo = accountService.getAccountByNubmer(numberTo);
        BigDecimal currentAccountSubtract = accountFrom.getBalance().subtract(amountTransaction);
        if (accountFrom.getCurrencyId() != accountTo.getCurrencyId()) {
            throw new CurrencyException();
        }
        if (currentAccountSubtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundException();
        }
        Transaction transaction =
                new Transaction(
                        amountTransaction,
                        numberFrom,
                        numberTo,
                        LocalDateTime.now(),
                        TransactionType.P2P,
                        accountFrom.getCurrencyId());
        transactionDao.insertTransaction(transaction);
    }

    public void doPayInCashToAccount(String number, BigDecimal amount) {
        Account accountByNumber = accountService.getAccountByNubmer(number);
        Transaction transaction =
                new Transaction(
                        amount,
                        "atm",
                        number,
                        LocalDateTime.now(),
                        TransactionType.PAY_IN,
                        accountByNumber.getCurrencyId());
        transactionDao.insertTransaction(transaction);
    }

    public void doPayOutMoneyToCash(String number, BigDecimal withdrawal) {
        Account accountByNumber = accountService.getAccountByNubmer(number);
        BigDecimal subtract = accountByNumber.getBalance().subtract(withdrawal);
        if (subtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundException();
        }
        Transaction transaction =
                new Transaction(
                        withdrawal,
                        number,
                        "atm",
                        LocalDateTime.now(),
                        TransactionType.PAY_OUT,
                        accountByNumber.getCurrencyId());
        transactionDao.insertTransaction(transaction);
    }

    public BigDecimal getBalanceByNumber(String number) {
        return accountService.getAccountByNubmer(number).getBalance();
    }
}
