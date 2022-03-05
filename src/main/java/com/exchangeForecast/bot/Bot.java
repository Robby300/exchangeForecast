package com.exchangeForecast.bot;

import com.exchangeForecast.command.CommandContainer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public final class Bot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final CommandContainer commandContainer;

    public Bot(String botName, String botToken, CommandContainer commandContainer) {
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        this.commandContainer = commandContainer;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }
}
