package org.atm.web.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import jdk.jfr.ContentType;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;

public class Client {

    public static String meth() throws IOException, InterruptedException {
        /*HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx"))
                .header("Content-Type", "text/xml; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofFile(Path.of("C:\\PROJECT\\ATM\\src\\main\\resources\\zapros.xml")))
                .build();
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of("C:\\PROJECT\\ATM\\src\\main\\resources\\otvet.xml")));
        System.out.println(response.statusCode());
        System.out.println(response.body());*/


        RestTemplate template = new RestTemplate();
        URI uri = UriComponentsBuilder.fromHttpUrl("https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx").build().toUri();
        String xmlzapros = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <GetCursOnDateXML xmlns=\"http://web.cbr.ru/\">\n" +
                "      <On_date>"+ LocalDate.now()+"</On_date>\n" +
                "    </GetCursOnDateXML>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        /*HttpEntity<String> request =
                new HttpEntity<>(xmlzapros);
        request.getHeaders().set("Content-Type","text/xml; charset=utf-8");
        template.postForObject(uri,request,String.class);
*/



        RequestEntity<String> requestEntity = RequestEntity.post(uri)
                .header("Content-Type","text/xml; charset=utf-8")
                .body(xmlzapros);

        ResponseEntity<String> response = template.exchange(requestEntity, String.class);
        String body = response.getBody();
        return body;
    }
}
