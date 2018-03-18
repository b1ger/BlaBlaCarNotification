package com.blablacarnotification.Voyage;

import com.blablacarnotification.Json.Trip;
import com.blablacarnotification.Parser.BlaBlaCarClient;
import com.blablacarnotification.Parser.Parser;
import com.blablacarnotification.Utils.Params;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voyage implements Runnable {
    private Parser parser;
    private BlaBlaCarClient client;
    private Map<String, String> params = new HashMap<>();
    private Map<String, Trip> trips = new HashMap<>();

    public Voyage() {
        params.put("from", "Kiev");
        params.put("to", "Kaniv");
        params.put("locale", "uk_UA");
        params.put("date", "2018-03-19");
        client = new BlaBlaCarClient();
        parser = new Parser();
    }

    @Override
    public void run() {
        List<Trip> currentTrips;
        while (! Thread.currentThread().isInterrupted()) {
            currentTrips = parser.process(client.connect(params));
            if(currentTrips != null) {
                for (Trip trip : currentTrips) {
                    if (!trips.containsKey(trip.id)) {
                        send(trip.toString());
                        trips.put(trip.id, trip);
                    }
                }
            }
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void send(String message) {
        TelegramBot bot = new TelegramBot(Params.BOT_TOKEN);
        SendMessage request = new SendMessage(Params.CHAT_ID, message)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(1)
                .replyMarkup(new ForceReply());
        bot.execute(request);
    }
}
