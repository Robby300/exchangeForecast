package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Привет. Я bot. Я буду прогнозировать курсы валют, " +
            "Я еще маленький и только учусь.";


    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {
        sendBotMessageService.sendMessage(START_MESSAGE);
    }
}
