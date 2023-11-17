package org.atm.web.controller;


import lombok.RequiredArgsConstructor;
import org.atm.service.BankOperationService;
import org.atm.web.model.P2PRequestParams;
import org.atm.web.model.PayInRequestParams;
import org.atm.web.model.PayOutRequestParams;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class AtmController {
    private final BankOperationService bankOperationService;


    @PostMapping("/p2p")
    private void transactionP2P(@RequestBody P2PRequestParams p2PRequestParams) {
        bankOperationService.doP2P(p2PRequestParams.getAccountNumberFrom(), p2PRequestParams.getAccountNumberTo(), p2PRequestParams.getAmount());
    }


    @PostMapping("/pay-in")
    private void payInCashToAccount(@RequestBody PayInRequestParams payInRequestParams) {
        bankOperationService.doPayInCashToAccount(payInRequestParams.getAccountNumber(), payInRequestParams.getAmount());
    }

    @PostMapping("/pay-out")
    private void payOutMoneyToCash(@RequestBody PayOutRequestParams payOutRequestParams) {
        bankOperationService.doPayOutMoneyToCash(payOutRequestParams.getAccountNumber(), payOutRequestParams.getWithdrawal());

    }

    @GetMapping("/show-balance/{number}")
    private BigDecimal showAccountBalanceByNumber(@PathVariable String number) {
        return bankOperationService.getBalanceByNumber(number);
    }

}
