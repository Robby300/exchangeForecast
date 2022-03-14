package com.exchangeForecast.bot;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.CommandContainer;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import com.exchangeForecast.service.outputServcie.SendBotMessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.exchangeForecast.command.CommandName.NO;

public final class Bot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

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
        return "test_robby300_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            try {
                String commandIdentifier = message.split("\\s")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } catch (Exception e) {
                logger.info("{} команда не прошла валидацию.", message);
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
                throw new NotValidException("Команда не прошла валидацию.");
            }
        }
    }
}
