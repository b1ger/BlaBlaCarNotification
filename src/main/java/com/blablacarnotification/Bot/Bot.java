package com.blablacarnotification.Bot;

import com.blablacarnotification.Utils.Params;
import com.blablacarnotification.Voyage.Voyage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.Map;

public class Bot extends TelegramLongPollingBot {
    private Map<Long, Voyage> trips = new HashMap<>();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals("/start")) {
                trips.put(message.getChatId(), new Voyage());
                trips.get(message.getChatId()).setChatId(message.getChatId());
            } else if (message.getText().contains("/set_from")) {
                setFrom(message);
            } else if (message.getText().contains("/set_to")) {
                setTo(message);
            } else if (message.getText().contains("/set_date")) {
                setDate(message);
            } else if (message.getText().equals("/search")) {
                if (checkParams(message)) {
                    Thread thread = new Thread(trips.get(message.getChatId()));
                    thread.setDaemon(true);
                    thread.start();
                }
            } else if (message.getText().equals("/check")) {
                check(message);
            } else if (message.getText().equals("/off")) {
                Voyage voyage = trips.get(message.getChatId());
                voyage.terminate();
                trips.remove(message.getChatId());
            } else {
                sendMsg(message, "Unknown command!");
            }
        }
    }

    public void sendMsg(Message message, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return Params.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Params.BOT_TOKEN;
    }

    private void setFrom(Message message) {
        String command = message.getText();
        String[] args = command.split(" ");
        String response;
        if (args.length == 2) {
            Voyage voyage = trips.get(message.getChatId());
            voyage.setFrom(args[1]);
            response = "Departure place is set";
        } else {
            response = "Dear, you use incorrect command. Set departure place like - /set_from London";
        }
        sendMsg(message, response);
    }

    private void setTo(Message message) {
        String command = message.getText();
        String[] args = command.split(" ");
        String response;
        if (args.length == 2) {
            Voyage voyage = trips.get(message.getChatId());
            voyage.setTo(args[1]);
            response = "Arrival place is set";
        } else {
            response = "Dear, you use incorrect command. Set arrival place like - /set_to Paris";
        }
        sendMsg(message, response);
    }

    private void setDate(Message message) {
        String command = message.getText();
        String[] args = command.split(" ");
        String response;
        if (args.length == 2) {
            Voyage voyage = trips.get(message.getChatId());
            voyage.setDate(args[1]);
            response = "Date is set";
        } else {
            response = "Dear, you use incorrect command. Set date like - /set_date 2020-03-23";
        }
        sendMsg(message, response);
    }

    private boolean checkParams(Message message) {
        Voyage voyage = trips.get(message.getChatId());
        boolean from = voyage.getFrom() == null ? false : true;
        boolean to = voyage.getTo() == null ? false : true;
        boolean date = voyage.getDate() == null ? false : true;
        boolean locale = voyage.getLocale() == null ? false : true;

        if (from && to && date && locale) {
            return  true;
        }

        return false;
    }

    private void check(Message message) {
        Voyage voyage = trips.get(message.getChatId());
        try {
            sendMsg(message,
                    "Your parameters:\n" +
                            "from - " + voyage.getFrom() + ";\n" +
                            "to - " + voyage.getTo() + ";\n" +
                            "date - " + voyage.getDate() + "."
            );
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            sendMsg(message, "Use command /start to create trip");
        }
    }
}

