package com.exchangeForecast.bot;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.CommandContainer;
import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import com.exchangeForecast.service.outputServcie.SendBotMessageServiceImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.exchangeForecast.command.CommandName.NO;

public final class Bot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "";

    private final String BOT_NAME = "test_robby300_bot";
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private final RatesCash cash = new RatesCash();

    private final SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(this);
    private final CommandContainer commandContainer = new CommandContainer(sendBotMessageService, cash);

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
