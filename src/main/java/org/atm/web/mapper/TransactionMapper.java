package org.atm.web.mapper;

import org.atm.service.BankOperationService;
import org.atm.web.model.request.P2PRequestParams;
import org.atm.web.model.request.PayInRequestParams;
import org.atm.web.model.request.PayOutRequestParams;
import org.atm.web.model.response.P2PResponseDto;
import org.atm.web.model.response.PayInResponseDto;
import org.atm.web.model.response.PayOutResponseDto;
import org.atm.web.model.response.ShowBalanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "numberFrom", source = "entity.accountNumberFrom")
    @Mapping(target = "numberTo", source = "entity.accountNumberTo")
    @Mapping(target = "amountTransaction", source = "entity.amount")
    P2PResponseDto p2PRequestParamsToP2PResponseDto(P2PRequestParams entity);

    @Mapping(target = "number", source = "entity.accountNumber")
    @Mapping(target = "amountTransaction", source = "entity.amount")
    PayInResponseDto payInRequestParamsToPayInResponseDto(PayInRequestParams entity);

    @Mapping(target = "number", source = "entity.accountNumber")
    @Mapping(target = "withdrawal", source = "entity.withdrawal")
    PayOutResponseDto payOutRequestParamsToPayOutResponseDto(PayOutRequestParams entity);

    @Mapping(
            target = "balance",
            expression = "java(bankOperationService.getBalanceByNumber(number))")
    ShowBalanceDto showBalance(BankOperationService bankOperationService, String number);
}
