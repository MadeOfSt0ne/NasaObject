package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "";
    public static final String BOT_USERNAME = "pictures7_bot";
    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=AzdkXwVUj2azyVYb7fTEUViwEa5qcGBPaOnpP6do";
    public static long chat_id;

    public TelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chat_id = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет, я бот NASA! Я высылаю ссылки на картинки по запросу. " +
                            "Напоминаю, что картинки на сайте NASA обновляются раз в сутки");
                    break;
                case "/give":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage("Я не понимаю :( " + update.getMessage().getText());
            }
        }
    }

    private void sendMessage(String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
