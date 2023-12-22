package org.atm.web.client;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import org.atm.web.model.response.CurrencyDto;
import java.util.ArrayList;
import java.util.List;

public class ParserXml {

    @SneakyThrows
    public static List<CurrencyDto> parseXml(String content) {
        String currentCode;
        List<CurrencyDto> list = new ArrayList<>();
        Document document = XmlUtil.fromXML(content);
        SoapParser parser = new SoapParser(document);
        SoapMessage soap = parser.parse();
        String str = XmlUtil.toXML(soap.getDocument(), true);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbf.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(new StringReader(str)));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("ValuteCursOnDate");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                currentCode = eElement
                        .getElementsByTagName("Vcode")
                        .item(0)
                        .getTextContent();
                if (currentCode.equals("978") || currentCode.equals("840")) {
                    CurrencyDto currency = new CurrencyDto();
                    currency.setRate(eElement
                            .getElementsByTagName("Vcurs")
                            .item(0)
                            .getTextContent());
                    currency.setCode(eElement
                            .getElementsByTagName("Vcode")
                            .item(0)
                            .getTextContent());
                    list.add(currency);
                }
            }
        }
        return list;
    }
}




