package com.blablacarnotification;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {

    public String getHtml(String urlString) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
            connection.addRequestProperty("Content-Typet", "text/plain");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                char[] buffer = new char[4096];
                int read;
                do {
                    if ((read = reader.read(buffer)) > 0) {
                        stringBuilder.append(new String(buffer, 0, read));
                    }
                } while (read > 0);
            } finally {
                connection.disconnect();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return stringBuilder.toString();
    }

    public void createDom(String uri) {
//        NodeList nodeList = null;
//        try {
//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document doc = builder.parse(uri);
//
//            XPath xPath = XPathFactory.newInstance().newXPath();
//            nodeList = (NodeList) xPath.evaluate("body", doc.getDocumentElement(), XPathConstants.NODESET);
//        } catch (IOException |
//                SAXException |
//                ParserConfigurationException |
//                XPathExpressionException ex) {
//            System.err.println(ex.getMessage());
//        }

        //TODO
    }
}
