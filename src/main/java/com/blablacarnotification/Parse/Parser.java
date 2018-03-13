package com.blablacarnotification.Parse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Parser implements ParserInterface {

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

    private String getPathToFile() {
        return Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                .substring(0, Parser.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                        .indexOf("target")) + "trips.json";
    }

    @Override
    public String getTrips() {
        try {
            URL url = new URL(getReqUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("Response code: " + connection.getResponseCode());
            InputStream is = connection.getInputStream();
            writeToJson(new String(requestBodyToArray(is), StandardCharsets.UTF_8));

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    private void writeToJson(String resp) {
        File json = new File(getPathToFile());
        try (OutputStream os = new FileOutputStream(json)) {
            os.write(resp.getBytes("UTF8"));
            os.flush();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private byte[] requestBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int r;
        do {
            r = is.read(buf);
            if (r > 0) {
                bos.write(buf, 0, r);
            }
        } while (r != -1);

        return bos.toByteArray();
    }


    public String getReqUrl() {
        return reqUrl;
    }
}
