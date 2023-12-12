package org.atm.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atm.db.TransactionDao;
import org.atm.db.model.Account;
import org.atm.model.Transaction;
import org.atm.model.TransactionType;
import org.atm.utils.CurrencyException;
import org.atm.utils.InsufficientFundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankOperationService {

    private final TransactionDao transactionDao;
    private final AccountService accountService;

    public void doP2P(String numberFrom, String numberTo, BigDecimal amountTransaction) {
        Account accountFrom = accountService.getAccountByNumber(numberFrom);
        Account accountTo = accountService.getAccountByNumber(numberTo);
        BigDecimal currentAccountSubtract = accountFrom.getBalance().subtract(amountTransaction);
        if (accountFrom.getCurrencyId() != accountTo.getCurrencyId()) {
            throw new CurrencyException("Переводы на счёт с другой валютой запрещены!");
        }
        if (currentAccountSubtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundException("На Вашем счёте недостаточно средств!");
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
        log.info(
                "BankOperationService: transaction from {} to {} done",
                transaction.getAccountFrom(),
                transaction.getAccountTo());
    }

    public void doPayInCashToAccount(String number, BigDecimal amount) {
        Account accountByNumber = accountService.getAccountByNumber(number);
        Transaction transaction =
                new Transaction(
                        amount,
                        "atm",
                        number,
                        LocalDateTime.now(),
                        TransactionType.PAY_IN,
                        accountByNumber.getCurrencyId());
        transactionDao.insertTransaction(transaction);
        log.info(
                "BankOperationService: transaction from {} to {} done",
                transaction.getAccountFrom(),
                transaction.getAccountTo());
    }

    public void doPayOutMoneyToCash(String number, BigDecimal withdrawal) {
        Account accountByNumber = accountService.getAccountByNumber(number);
        BigDecimal subtract = accountByNumber.getBalance().subtract(withdrawal);
        if (subtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundException("На Вашем счёте недостаточно средств!");
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
        log.info(
                "BankOperationService: transaction from {} to {} done",
                transaction.getAccountFrom(),
                transaction.getAccountTo());
    }

    public BigDecimal getBalanceByNumber(String number) {
        log.info("BankOperationService: get balance by number {}", number);
        return accountService.getAccountByNumber(number).getBalance();
    }
}
