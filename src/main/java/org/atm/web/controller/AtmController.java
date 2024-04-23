package org.atm.web.controller;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.atm.db.model.Role;
import org.atm.db.model.User;
import org.atm.service.AccountService;
import org.atm.service.BankOperationService;
import org.atm.web.mapper.ResponseMapper;
import org.atm.web.model.request.HashMatcher;
import org.atm.web.model.request.P2PRequestParams;
import org.atm.web.model.request.PayInRequestParams;
import org.atm.web.model.request.PayOutRequestParams;
import org.atm.web.model.response.P2PResponseDto;
import org.atm.web.model.response.PayInResponseDto;
import org.atm.web.model.response.PayOutResponseDto;
import org.atm.web.model.response.ShowBalanceDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operation")
@RequiredArgsConstructor
public class AtmController {
    private final PasswordEncoder passwordEncoder;
    private final BankOperationService bankOperationService;
    private final ResponseMapper mapper;
    private final AccountService accountService;

    @PostMapping("/p2p")
    public P2PResponseDto transactionP2P(
            @RequestBody P2PRequestParams p2PRequestParams, @AuthenticationPrincipal User user) {
        if (user.getRoleId() == 2) {
            accountService.checkAccountBelongToUserByAccountNumber(
                    user, p2PRequestParams.getAccountNumberFrom());
        }
        bankOperationService.doP2P(
                p2PRequestParams.getAccountNumberFrom(),
                p2PRequestParams.getAccountNumberTo(),
                p2PRequestParams.getAmountFrom());
        return mapper.p2PRequestParamsToP2PResponseDto(p2PRequestParams);
    }

    @PostMapping("/pay-in")
    public PayInResponseDto payInCashToAccount(
            @RequestBody PayInRequestParams payInRequestParams,
            @AuthenticationPrincipal User user) {
        if (user.getRoleId() == 2) {
            accountService.checkAccountBelongToUserByAccountNumber(
                    user, payInRequestParams.getAccountNumber());
        }
        bankOperationService.doPayInCashToAccount(
                payInRequestParams.getAccountNumber(), payInRequestParams.getAmount());
        return mapper.payInRequestParamsToPayInResponseDto(payInRequestParams);
    }

    @PostMapping("/pay-out")
    public PayOutResponseDto payOutMoneyToCash(
            @RequestBody PayOutRequestParams payOutRequestParams,
            @AuthenticationPrincipal User user) {
        if (user.getRoleId() == 2) {
            accountService.checkAccountBelongToUserByAccountNumber(
                    user, payOutRequestParams.getAccountNumber());
        }
        bankOperationService.doPayOutMoneyToCash(
                payOutRequestParams.getAccountNumber(), payOutRequestParams.getWithdrawal());
        return mapper.payOutRequestParamsToPayOutResponseDto(payOutRequestParams);
    }

    @GetMapping("/show-balance/{number}")
    public ShowBalanceDto showAccountBalanceByNumber(
            @PathVariable String number, @AuthenticationPrincipal User user) {
        if (user.getAuthorities().stream()
                .noneMatch(
                        grantedAuthority ->
                                grantedAuthority.getAuthority().equals(Role.ADMIN.name()))) {
            accountService.checkAccountBelongToUserByAccountNumber(user, number);
        }
        BigDecimal balance = bankOperationService.getBalanceByNumber(number);
        return mapper.balanceToShowBalanceDto(balance);
    }

    @GetMapping("/show-all-balance/{login}")
    public ShowBalanceDto showAllAccountBalanceByLogin(
            @PathVariable String login, @AuthenticationPrincipal User user) {
        if (user.getRoleId() == 2) {
            accountService.checkAccountBelongToUserByLogin(user, login);
        }
        BigDecimal balance = bankOperationService.getBalanceByLogin(login);
        return mapper.balanceToShowBalanceDto(balance);
    }

    @PreAuthorize("hasAuthority('ADMIN')") // todo сделать норм для остальных
    @GetMapping("/for-admin/{number}")
    public ShowBalanceDto forAdmin(@PathVariable String number) {
        BigDecimal balance = bankOperationService.getBalanceByNumber(number);
        return mapper.balanceToShowBalanceDto(balance);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/hashfunc/{rowpass}")
    public String tryHash (@PathVariable String rowpass){
        return passwordEncoder.encode(rowpass);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/hashfunc/match")
    public Boolean matchHash (@RequestBody HashMatcher hashMatcher){
        return passwordEncoder.matches(hashMatcher.getPass(),hashMatcher.getHash());
    }

}
