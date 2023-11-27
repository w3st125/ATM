package org.atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.atm.service.BankOperationService;
import org.atm.web.model.request.P2PRequestParams;
import org.atm.web.model.request.PayInRequestParams;
import org.atm.web.model.request.PayOutRequestParams;
import org.atm.web.model.response.P2PResponseDto;
import org.atm.web.model.response.PayInResponseDto;
import org.atm.web.model.response.PayOutResponseDto;
import org.atm.web.model.response.ShowBalanceDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class AtmController {
    private final BankOperationService bankOperationService;

    @PostMapping("/p2p")
    private P2PResponseDto transactionP2P(@RequestBody P2PRequestParams p2PRequestParams) {
        bankOperationService.doP2P(
                p2PRequestParams.getAccountNumberFrom(),
                p2PRequestParams.getAccountNumberTo(),
                p2PRequestParams.getAmount());
        P2PResponseDto p2PResponseDto = new P2PResponseDto();
        p2PResponseDto.setAmountTransaction(p2PRequestParams.getAmount());
        p2PResponseDto.setNumberFrom(p2PRequestParams.getAccountNumberFrom());
        p2PResponseDto.setNumberTo(p2PRequestParams.getAccountNumberTo());
        return p2PResponseDto;
    }

    @PostMapping("/pay-in")
    private PayInResponseDto payInCashToAccount(
            @RequestBody PayInRequestParams payInRequestParams) {
        bankOperationService.doPayInCashToAccount(
                payInRequestParams.getAccountNumber(), payInRequestParams.getAmount());
        PayInResponseDto payInResponseDto = new PayInResponseDto();
        payInResponseDto.setAmountTransaction(payInRequestParams.getAmount());
        payInResponseDto.setNumber(payInRequestParams.getAccountNumber());
        return payInResponseDto;
    }

    @PostMapping("/pay-out")
    private PayOutResponseDto payOutMoneyToCash(@RequestBody PayOutRequestParams payOutRequestParams) {
        bankOperationService.doPayOutMoneyToCash(
                payOutRequestParams.getAccountNumber(), payOutRequestParams.getWithdrawal());
        PayOutResponseDto payOutResponseDto = new PayOutResponseDto();
        payOutResponseDto.setNumber(payOutRequestParams.getAccountNumber());
        payOutResponseDto.setWithdrawal(payOutRequestParams.getWithdrawal());
        return payOutResponseDto;
    }

    @GetMapping("/show-balance/{number}")
    private ShowBalanceDto showAccountBalanceByNumber(@PathVariable String number) {
        ShowBalanceDto showBalanceDto = new ShowBalanceDto();
        showBalanceDto.setBalance(bankOperationService.getBalanceByNumber(number));
        return showBalanceDto;
    }
}
