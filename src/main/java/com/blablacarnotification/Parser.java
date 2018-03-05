package com.blablacarnotification;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
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

        return clearTags(stringBuilder.toString());
    }

    public void writeToXml(String uri) {
        Document doc = Jsoup.parse(uri);

        try(FileWriter writer = new FileWriter(Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(0, Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath().indexOf("target")) + "trips.xml")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
            Elements trips = doc.getElementsByClass("trip-search-results");
            writer.write(trips.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void createDomDocument() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(new File(Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(0, Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath().indexOf("target")) + "trips.xml"));
            NodeList list = document.getDocumentElement().getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                System.out.println(list.item(i).getNodeName());
            }

        } catch (ParserConfigurationException |
                SAXException |
                IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private String clearTags(String result) {
        result = result.replaceAll("itemtype=\"(.*)\"", "");
        result = result.replaceAll("itemscope ", "");
        result = result.replaceAll("<meta(.*)>", "");
        result = result.replaceAll("<img(.*)>", "");
        result = result.replaceAll("<use(.*)></use>", "");
        result = result.replaceAll("<svg(.*)></svg>", "");
        result = result.replaceAll("br", "");
        result = result.replaceAll("\n" +
                "                            ", "");
        return result;
    }
}
