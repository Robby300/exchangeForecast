package com.exchangeForecast.bot;

import com.exchangeForecast.cash.RatesInMemory;
import com.exchangeForecast.command.CommandContainer;
import com.exchangeForecast.command.CommandUtils;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.service.outputServcie.SendMessageService;
import com.exchangeForecast.service.outputServcie.SendMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.exchangeForecast.command.CommandName.NO;

public final class Bot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private final RatesInMemory cash = new RatesInMemory();

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return "test_robby300_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String message = CommandUtils.getMessage(update);
        SendMessageService sendMessageService = new SendMessageServiceImpl(this, update);
        CommandContainer commandContainer = new CommandContainer(sendMessageService, cash);
        try {
            String commandIdentifier = message.split("\\s")[0].toLowerCase();
            commandContainer.retrieveCommand(commandIdentifier).execute(message);
        } catch (Exception e) {
            logger.info("{} команда не прошла валидацию.", message);
            commandContainer.retrieveCommand(NO.getCommandName()).execute(message);
            throw new NotValidException("Команда не прошла валидацию.");
        }
    }
}

