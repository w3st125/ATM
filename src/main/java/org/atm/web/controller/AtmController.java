package org.atm.web.controller;

import lombok.RequiredArgsConstructor;
import org.atm.db.model.User;
import org.atm.service.AccountService;
import org.atm.service.BankOperationService;
import org.atm.web.mapper.ResponseMapper;
import org.atm.web.model.request.P2PRequestParams;
import org.atm.web.model.request.PayInRequestParams;
import org.atm.web.model.request.PayOutRequestParams;
import org.atm.web.model.response.P2PResponseDto;
import org.atm.web.model.response.PayInResponseDto;
import org.atm.web.model.response.PayOutResponseDto;
import org.atm.web.model.response.ShowBalanceDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class AtmController {
    private final BankOperationService bankOperationService;
    private final ResponseMapper mapper;
    private final AccountService accountService;

    @PostMapping("/p2p")
    private P2PResponseDto transactionP2P(
            @RequestBody P2PRequestParams p2PRequestParams, @AuthenticationPrincipal User user) {
        if(user.getRoleId()==2){
        accountService.checkAccountBelongToUserByNumber(user,p2PRequestParams.getAccountNumberFrom());}
        bankOperationService.doP2P(
                p2PRequestParams.getAccountNumberFrom(),
                p2PRequestParams.getAccountNumberTo(),
                p2PRequestParams.getAmountFrom());
        return mapper.p2PRequestParamsToP2PResponseDto(p2PRequestParams);
    }

    @PostMapping("/pay-in")
    private PayInResponseDto payInCashToAccount(
            @RequestBody PayInRequestParams payInRequestParams, @AuthenticationPrincipal User user) {
        if(user.getRoleId()==2){
        accountService.checkAccountBelongToUserByNumber(user,payInRequestParams.getAccountNumber());}
        bankOperationService.doPayInCashToAccount(
                payInRequestParams.getAccountNumber(), payInRequestParams.getAmount());
        return mapper.payInRequestParamsToPayInResponseDto(payInRequestParams);
    }

    @PostMapping("/pay-out")
    private PayOutResponseDto payOutMoneyToCash(
            @RequestBody PayOutRequestParams payOutRequestParams, @AuthenticationPrincipal User user) {
        if(user.getRoleId()==2){
        accountService.checkAccountBelongToUserByNumber(user,payOutRequestParams.getAccountNumber());}
        bankOperationService.doPayOutMoneyToCash(
                payOutRequestParams.getAccountNumber(), payOutRequestParams.getWithdrawal());
        return mapper.payOutRequestParamsToPayOutResponseDto(payOutRequestParams);
    }

    @GetMapping("/show-balance/{number}")
    private ShowBalanceDto showAccountBalanceByNumber(@PathVariable String number, @AuthenticationPrincipal User user) {
        if(user.getRoleId()==2){
        accountService.checkAccountBelongToUserByNumber(user, number);}
        BigDecimal balance = bankOperationService.getBalanceByNumber(number);
        return mapper.balanceToShowBalanceDto(balance);
    }

    @GetMapping("/show-all-balance/{login}")
    private ShowBalanceDto showAllAccountBalanceByLogin(@PathVariable String login, @AuthenticationPrincipal User user) {
        if(user.getRoleId()==2){
        accountService.checkAccountBelongToUserByLogin(user, login);}
        BigDecimal balance = bankOperationService.getBalanceByLogin(login);
        return mapper.balanceToShowBalanceDto(balance);
    }
}
