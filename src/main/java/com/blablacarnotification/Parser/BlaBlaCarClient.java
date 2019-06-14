package com.blablacarnotification.Parser;

import com.blablacarnotification.Utils.Params;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class BlaBlaCarClient {

    private String reqUrl;
    private final int LIMIT = 50;
    public int count = 1;

    public InputStream connect(Map<String, String> params) {
        reqUrl = buildReqUrl(params);

        InputStream is;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();

            System.out.print("Request #" + count++ + "; Status: " + connection.getResponseCode() + "; ");
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            return null;
        }

        return is;
    }

    private String buildReqUrl(Map params) {
        String url;
        url = "https://public-api.blablacar.com/api/v2/trips" +
                "?key=" + Params.DEV_TOKEN +
                "&fn=" + params.get("from") +
                "&tn=" + params.get("to") +
                "&locale=" + params.get("locale") +
                "&_format=json" +
                "&db=" + params.get("date") +
                "&sort=trip_date" +
                "&order=asc" +
                "&limit=" + LIMIT;

        return url;
    }
}
