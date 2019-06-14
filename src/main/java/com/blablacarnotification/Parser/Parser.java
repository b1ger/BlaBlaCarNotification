package com.blablacarnotification.Parser;

import com.blablacarnotification.Json.Json;
import com.blablacarnotification.Json.TripJsonModel;
import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser {

    private Gson gson;

    public Parser() {
        gson = new GsonBuilder().create();
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

    public List<TripJsonModel> process(InputStream is) {
        try {
            List<TripJsonModel> tripList = new ArrayList<>();
            byte[] bytes = requestBodyToArray(is);
            String resp = new String(bytes, StandardCharsets.UTF_8);
            Json trips = gson.fromJson(resp, Json.class);
            Collections.addAll(tripList, trips.trips);
            System.out.println("Available trips: " + tripList.size() + ";");
            return tripList;
        } catch (JsonSyntaxException ex) {
            System.err.println("Json not available");
        }

        return null;
    }
}
