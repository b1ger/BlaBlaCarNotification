package com.blablacarnotification;

import com.blablacarnotification.Parse.Parser;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("from", "Kiev");
        params.put("to", "Kanev");
        params.put("locale", "uk_UA");
        params.put("date", "2018-03-16");

        Parser parser = new Parser(params);

        parser.getTrips();
    }
}
