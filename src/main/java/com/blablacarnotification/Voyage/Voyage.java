package com.blablacarnotification.Voyage;

import com.blablacarnotification.Json.TripJsonModel;
import com.blablacarnotification.Parser.BlaBlaCarClient;
import com.blablacarnotification.Parser.Parser;
import com.blablacarnotification.Utils.Params;
import com.blablacarnotification.Dao.TripDAO;
import com.blablacarnotification.Dao.TripDAOImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voyage implements Runnable {

    private volatile boolean running = false;
    private Parser parser;
    private BlaBlaCarClient client;
    private Map<String, String> params = new HashMap<>();
    private String from;
    private String to;
    private String date;
    private String locale = "uk_UA";
    private long chatId;

    private TelegramBot telegramBot;
    private TripManager tripManager;
    private TripDAO tripDAO;

    public Voyage(Long chatId) {
        this.chatId = chatId;
        client = new BlaBlaCarClient();
        parser = new Parser();
        telegramBot = new TelegramBot(Params.BOT_TOKEN);
        this.tripDAO = TripDAOImpl.getInstance();
        this.tripManager = new TripManager(this.tripDAO, this);
    }

    @Override
    public void run() {
        List<TripJsonModel> currentTrips;
        initParams();
        System.out.println("Chat id: " + chatId);
        startManager();
        try {
            while (running) {
                currentTrips = parser.process(client.connect(params));
                if(currentTrips != null && currentTrips.size() > 0) {
                    tripDAO.save(chatId, currentTrips, tripManager);
                }
                try {
                    Thread.sleep(1000 * 60 * 2);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception ex) {
            send("Something went wrong. Start the search again.");
            ex.printStackTrace();
        }
    }

    public void send(String message) {
        SendMessage request = new SendMessage(chatId, message)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
                //.disableNotification(true);
        this.telegramBot.execute(request);
    }

    private void initParams() {
        params.put("from", from);
        params.put("to", to);
        params.put("date", date);
        params.put("locale", locale);
    }

    private void startManager() {
        Thread thread = new Thread(this.tripManager);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        tripDAO.clear(chatId);
        Thread.currentThread().interrupt();
        send("Something went wrong. Start the search again.");
    }

    public void terminate() {
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getChatId() {
        return chatId;
    }
}
