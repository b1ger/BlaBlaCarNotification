package com.blablacarnotification.Parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser implements ParserInterface {

    private String xmlFile;
    private String url;

    public Parser(String[] args) {
        this.url = buildUrl(args);
        this.xmlFile = getPathToFile();
    }

    private String buildUrl(String[] args) {
        String url;
        url = "https://www.blablacar.com.ua/ride-sharing/" +
                args[0] + "/" +
                args[1] + "/" +
                "?db=" + args[2].replaceAll("/", "%2F");

        return url;
    }

    private String getPathToFile() {
        return Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                .substring(0, Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                        .indexOf("target")) + "trips.xml";
    }

    @Override
    public String getHtml() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(getUrl());

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
                reader.close();
            } finally {
                connection.disconnect();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return clearTags(stringBuilder.toString());
    }

    @Override
    public boolean writeToXml(String html) {
        Document doc = Jsoup.parse(html);
        try(FileWriter writer = new FileWriter(getXmlFile())) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
            Elements trips = doc.getElementsByClass("trip-search-results");
            writer.write(trips.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }

        return true;
    }

    public String getXmlFile() {
        return this.xmlFile;
    }

    public String getUrl() {
        return url;
    }

    private String clearTags(String result) {
        result = result.replaceAll("itemtype=\"(.*)\"", "");
        result = result.replaceAll("itemscope ", "");
        result = result.replaceAll("<meta(.*)>", "");
        result = result.replaceAll("<img(.*)>", "");
        result = result.replaceAll("<use(.*)></use>", "");
        result = result.replaceAll("<svg(.*)></svg>", "");
        result = result.replaceAll("(?i)<br */?>", "");
        result = result.replaceAll("\\u00a0", "");
        return result;
    }
}
