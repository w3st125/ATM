package org.atm.service;

import lombok.RequiredArgsConstructor;
import org.atm.db.TransactionDao;
import org.atm.db.model.Account;
import org.atm.model.Transaction;
import org.atm.model.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BankOperationService {

    private final TransactionDao transactionDao;
    private final AccountService accountService;

    public void doP2P(Long userId, String toNumber, BigDecimal amountTransaction) {
        Account accountByUser = accountService.getAccountByUserId(userId);
        BigDecimal currentUserAccountSubtract = accountByUser.getBalance().subtract(amountTransaction);
        if (currentUserAccountSubtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("На счете недостаточно средств");
        }
        Transaction transaction = new Transaction(amountTransaction, accountByUser.getNumber(), toNumber, LocalDateTime.now(), TransactionType.P2P);
        transactionDao.insertTransaction(transaction);
    }

    public void doPayInCashToAccount(Long userId, BigDecimal amount) {
        Account accountByUser = accountService.getAccountByUserId(userId);
        Transaction transaction = new Transaction(amount, "atm", accountByUser.getNumber(), LocalDateTime.now(), TransactionType.PAY_IN);
        transactionDao.insertTransaction(transaction);
    }

    public void doPayOutMoneyToCash(Long userId, BigDecimal withdrawal) {
        Account accountByUser = accountService.getAccountByUserId(userId);
        BigDecimal subtract = accountByUser.getBalance().subtract(withdrawal);
        if (subtract.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("На счете недостаточно средств");
        }
        Transaction transaction = new Transaction(withdrawal, accountByUser.getNumber(), "atm", LocalDateTime.now(), TransactionType.PAY_OUT);
        transactionDao.insertTransaction(transaction);
    }

    public BigDecimal getBalanceByUserId(Long userId) {
        return accountService.getAccountByUserId(userId).getBalance();
    }
}
