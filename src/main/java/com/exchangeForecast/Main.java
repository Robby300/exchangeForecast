package com.exchangeForecast;

import com.exchangeForecast.bot.Bot;
import com.exchangeForecast.ui.UserInterface;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class Main {

    private static final UserInterface userInterface = new UserInterface();
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
       /* try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(getenv.get("BOT_NAME"), getenv.get("BOT_TOKEN")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/

        userInterface.initialize();
    }
}
