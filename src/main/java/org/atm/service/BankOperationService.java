package org.atm.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atm.db.CurrencyPairDao;
import org.atm.db.TransactionDao;
import org.atm.db.model.Account;
import org.atm.model.CurrencyPair;
import org.atm.model.Transaction;
import org.atm.model.TransactionType;
import org.atm.utils.ATMUtils;
import org.atm.utils.InsufficientFundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankOperationService {
    private final CurrencyPairDao currencyPairDao;
    private final TransactionDao transactionDao;
    private final AccountService accountService;
    private final UserService userService;
    private final ExchangeRateService exchangeRateService;

    public void doP2P(String numberFrom, String numberTo, BigDecimal amountTransactionFrom) {
        BigDecimal exchangeRate;
        BigDecimal amountTransactionTo;
        Account accountFrom = accountService.getAccountByNumber(numberFrom);
        Account accountTo = accountService.getAccountByNumber(numberTo);
        for (CurrencyPair currencyPair :
                ATMUtils.createListPair(exchangeRateService.createListOfExchangeRate())) {
            currencyPairDao.updateColumnCurrencyPair(currencyPair);
            log.info("Пара {} добавлена", currencyPair);
        }
        BigDecimal currentAccountSubtract =
                accountFrom.getBalance().subtract(amountTransactionFrom);
        exchangeRate =
                currencyPairDao.findExchangeRateById(
                        accountFrom.getCurrencyId(), accountTo.getCurrencyId());
        amountTransactionTo = amountTransactionFrom.multiply(exchangeRate);
        if (currentAccountSubtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundException("На Вашем счёте недостаточно средств!");
        }
        Transaction transaction =
                new Transaction(
                        amountTransactionFrom,
                        amountTransactionTo,
                        numberFrom,
                        numberTo,
                        LocalDateTime.now(),
                        TransactionType.P2P,
                        accountFrom.getCurrencyId(),
                        accountTo.getCurrencyId());
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
                        amount,
                        "atm",
                        number,
                        LocalDateTime.now(),
                        TransactionType.PAY_IN,
                        accountByNumber.getCurrencyId(),
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
                        withdrawal,
                        number,
                        "atm",
                        LocalDateTime.now(),
                        TransactionType.PAY_OUT,
                        accountByNumber.getCurrencyId(),
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

    public BigDecimal getBalanceByLogin(String login) {
        BigDecimal totalSum;
        log.info("BankOperationService: get balance by login {}", login);
        totalSum =
                userService.getUserByLogin(login).getAccountList().stream()
                        .reduce(
                                BigDecimal.ZERO,
                                (total, account) ->
                                        total.add(
                                                account.getBalance()
                                                        .multiply(
                                                                currencyPairDao
                                                                        .findExchangeRateById(
                                                                                account
                                                                                        .getCurrencyId(),
                                                                                643L))),
                                BigDecimal::add);
        return totalSum.setScale(2, BigDecimal.ROUND_DOWN);
    }
}
