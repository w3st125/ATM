package org.atm.integration.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.w3c.dom.Document;

@Getter
@AllArgsConstructor
public class SoapMessage {
    private final Document document;
    private final boolean fault;
}
