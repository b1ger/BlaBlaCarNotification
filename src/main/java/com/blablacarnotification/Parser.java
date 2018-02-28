package com.blablacarnotification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {

    public String process(String urlString) {
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
}
