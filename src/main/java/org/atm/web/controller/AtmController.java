package org.atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.atm.service.BankOperationService;
import org.atm.web.mapper.ResponseMapper;
import org.atm.web.model.request.P2PRequestParams;
import org.atm.web.model.request.PayInRequestParams;
import org.atm.web.model.request.PayOutRequestParams;
import org.atm.web.model.response.P2PResponseDto;
import org.atm.web.model.response.PayInResponseDto;
import org.atm.web.model.response.PayOutResponseDto;
import org.atm.web.model.response.ShowBalanceDto;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class AtmController {
    private final BankOperationService bankOperationService;
    private final ResponseMapper mapper;

    @PostMapping("/p2p")
    private P2PResponseDto transactionP2P(@RequestBody P2PRequestParams p2PRequestParams) throws IOException, InterruptedException {
        bankOperationService.doP2P(
                p2PRequestParams.getAccountNumberFrom(),
                p2PRequestParams.getAccountNumberTo(),
                p2PRequestParams.getAmountFrom());
        return mapper.p2PRequestParamsToP2PResponseDto(p2PRequestParams);
    }

    @PostMapping("/pay-in")
    private PayInResponseDto payInCashToAccount(
            @RequestBody PayInRequestParams payInRequestParams) {
        bankOperationService.doPayInCashToAccount(
                payInRequestParams.getAccountNumber(), payInRequestParams.getAmount());
        return mapper.payInRequestParamsToPayInResponseDto(payInRequestParams);
    }

    @PostMapping("/pay-out")
    private PayOutResponseDto payOutMoneyToCash(
            @RequestBody PayOutRequestParams payOutRequestParams) {
        bankOperationService.doPayOutMoneyToCash(
                payOutRequestParams.getAccountNumber(), payOutRequestParams.getWithdrawal());
        return mapper.payOutRequestParamsToPayOutResponseDto(payOutRequestParams);
    }

    @GetMapping("/show-balance/{number}")
    private ShowBalanceDto showAccountBalanceByNumber(@PathVariable String number)  {
        BigDecimal balance = bankOperationService.getBalanceByNumber(number);
        return mapper.balanceToShowBalanceDto(balance);
    }
}
