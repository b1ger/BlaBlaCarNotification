package com.blablacarnotification.Parse;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataBuilder implements DataBuilderInterface {

    private Document domDocument;

    @Override
    public boolean createDomDocument(String pathToFile) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(pathToFile));
            setDomDocument(document);
        } catch (ParserConfigurationException |
                SAXException |
                IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List createModels() {
        //TODO
        return null;
    }

    @Override
    public boolean saveModels(List models) {
        //TODO
        return false;
    }

    private void setDomDocument(Document domDocument) {
        this.domDocument = domDocument;
    }

    public Document getDomDocument() {
        return domDocument;
    }
}
