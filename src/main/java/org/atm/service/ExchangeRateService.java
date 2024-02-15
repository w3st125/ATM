package org.atm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.atm.integration.client.Client;
import org.atm.integration.model.Currency;
import org.atm.integration.parser.ParserXmlService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ParserXmlService parserXmlService;
    private final Client client;

    public List<Currency> createListOfExchangeRate() {
        Currency currency = new Currency();
        currency.setRate("1");
        currency.setCode("643");
        List<Currency> list =
                parserXmlService.getExchangeRateOfEuroAndDollar(client.getSoapXmlMessage());
        list.add(currency);
        return list;
    }
}
