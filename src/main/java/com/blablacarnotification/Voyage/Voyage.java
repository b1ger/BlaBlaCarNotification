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
    private volatile boolean running = true;
    private Parser parser;
    private BlaBlaCarClient client;
    private Map<String, String> params = new HashMap<>();
    private Map<String, Trip> trips = new HashMap<>();
    private String from;
    private String to;
    private String date;
    private String locale = "uk_UA";
    private long chatId;

    public Voyage() {
        client = new BlaBlaCarClient();
        parser = new Parser();
    }

    @Override
    public void run() {
        List<Trip> currentTrips;
        initParams();
        while (running) {
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
                Thread.sleep(1000 * 60 * 2);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void send(String message) {
        TelegramBot bot = new TelegramBot(Params.BOT_TOKEN);
        SendMessage request = new SendMessage(chatId, message)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(1)
                .replyMarkup(new ForceReply());
        bot.execute(request);
    }

    private void initParams() {
        params.put("from", from);
        params.put("to", to);
        params.put("date", date);
        params.put("locale", locale);
    }

    public void terminate() {
        send("Service is stopped");
        running = false;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getLocale() {
        return locale;
    }
}
