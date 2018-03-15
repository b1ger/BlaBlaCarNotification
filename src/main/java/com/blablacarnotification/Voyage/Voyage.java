package com.blablacarnotification.Voyage;

import com.blablacarnotification.Json.Trip;
import com.blablacarnotification.Parser.Parser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voyage implements Runnable {
    private Map<String, Trip> trips = new HashMap<>();
    Parser parser;

    public Voyage() {
        Map<String, String> params = new HashMap<>();
        params.put("from", "Київ");
        params.put("to", "Канів");
        params.put("locale", "uk_UA");
        params.put("date", "2018-03-16");

        parser = new Parser(params);
    }

    @Override
    public void run() {
        List<Trip> currentTrips;
        while (! Thread.currentThread().isInterrupted()) {
            currentTrips = parser.readJson();
            System.out.println("Available trips: " + currentTrips.size());
            for (Trip trip : currentTrips) {
                if (!trips.containsKey(trip.id)) {
                    send(trip);
                    trips.put(trip.id, trip);
                }
            }
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
    }

    private void send(Trip trip) {
        String message = trip.toString();
        TelegramBot bot = new TelegramBot("587386724:AAF7sz3yQffbxF-Y9QGPc-C-Zy1U1S6YDWk");
        SendMessage request = new SendMessage(372998799, message)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(1)
                .replyMarkup(new ForceReply());
        bot.execute(request);
    }
}
