package com.blablacarnotification.Parser;

import com.blablacarnotification.Json.Json;
import com.blablacarnotification.Json.Trip;
import com.google.gson.*;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Parser {

    private String reqUrl;
    private String from;
    private String to;
    private String locale;
    private String date;
    private final int LIMIT = 50;

    private final String token = "68d3d382580f49fd8c104c0a7acbc2d3";

    public Parser(Map<String, String> params) {
        from = params.get("from");
        to = params.get("to");
        locale = params.get("locale");
        date = params.get("date");
        this.reqUrl = buildReqUrl();
    }

    private String buildReqUrl() {
        String url;
        url = "https://public-api.blablacar.com/api/v2/trips" +
                "?key=" + token +
                "&fn=" + from +
                "&tn=" + to +
                "&locale=" + locale +
                "&_format=json" +
                "&db=" + date +
                "&limit=" + LIMIT;

        return url;
    }

    private InputStream getDataFromResource() {
        InputStream is = null;
        try {
            URL url = new URL(getReqUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return is;
    }

    private byte[] requestBodyToArray(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[4096];
            int r;
            do {
                r = is.read(buf);
                if (r > 0) {
                    bos.write(buf, 0, r);
                }
            } while (r != -1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return bos.toByteArray();
    }

    public List<Trip> readJson() {
        List<Trip> tripList = new ArrayList<>();
        InputStream is = getDataFromResource();
        byte[] bytes = requestBodyToArray(is);
        String resp = new String(bytes);
        resp = StringEscapeUtils.unescapeJava(resp);
        writeToJson(resp);
        Gson gson = new GsonBuilder().create();
        Json trips = gson.fromJson(resp, Json.class);
        Collections.addAll(tripList, trips.trips);

        return tripList;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    private void writeToJson(String resp) {
        File json = new File(getPathToFile());
        try (OutputStream os = new FileOutputStream(json)) {
            os.write(resp.getBytes("UTF8"));
            os.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String getPathToFile() {
        return Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                .substring(0, Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                        .indexOf("target")) + "trips.json";
    }
}
