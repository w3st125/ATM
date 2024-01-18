package org.atm.integration.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.text.MessageFormat;
import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class Client {

    private static final String CB_REQUEST = """ 
            <?xml version="1.0" encoding="utf-8"?>
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <GetCursOnDateXML xmlns="http://web.cbr.ru/">
                  <On_date>{0}</On_date>
                </GetCursOnDateXML>
              </soap:Body>
            </soap:Envelope>
                            """; //todo посмотреть вариант через либу и xsd схема
    private final RestTemplate restTemplate;

    @Value("${cb-request-url}")
    private String urlForCbRequest;

    public String getSoapXmlMessage() {
        URI uri = UriComponentsBuilder.fromHttpUrl(urlForCbRequest).build().toUri();
        RequestEntity<String> requestEntity = RequestEntity.post(uri)
                .header("Content-Type", "text/xml; charset=utf-8")
                .body(MessageFormat.format(CB_REQUEST, LocalDate.now()));
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        return response.getBody();
    }
}
