package de.edirom.meigarage.xml;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RNGValidator implements XmlValidator {

    private static final Logger LOGGER = Logger.getLogger(RNGValidator.class);

    private final String schemeUrl;

    public RNGValidator(String schemeUrl) {
        if(schemeUrl == null){
            throw new IllegalArgumentException();
        }
        this.schemeUrl = schemeUrl;
    }

    public void validateXml(InputStream inputData, ErrorHandler errorHandler) throws SAXException, FileNotFoundException, IOException, Exception {

    }
}
