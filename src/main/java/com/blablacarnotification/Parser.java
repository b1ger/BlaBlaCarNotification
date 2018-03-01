package com.blablacarnotification;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.BufferedReader;
import java.io.FileWriter;
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
        String result = stringBuilder.toString();
        return result.replaceAll("<br> ", "");
    }

    public void createDom(String uri) {
        Document doc = Jsoup.parse(uri);

        try(FileWriter writer = new FileWriter("C:\\Users\\b.herashchenko\\IdeaProjects\\BlaBlaCarNotification\\trips.xml")) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
            writer.write(doc.getElementsByClass("trip-search-results").toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
