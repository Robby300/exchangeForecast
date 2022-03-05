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
       try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

           //bot.username=test_robby300_bot
           //bot.token=2067623158:AAGjETT5zTSEgJQqEojAvADITIMTsuRvmDI
            botsApi.registerBot(new Bot("test_robby300_bot","2067623158:AAGjETT5zTSEgJQqEojAvADITIMTsuRvmDI", commandContainer));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        userInterface.initialize();
    }
}
