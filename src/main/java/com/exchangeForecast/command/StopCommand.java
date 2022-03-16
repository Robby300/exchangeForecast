package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Stop {@link Command}.
 */
public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String STOP_MESSAGE = "До свидания \uD83D\uDE1F.";

    public StopCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {
        sendBotMessageService.sendMessage(STOP_MESSAGE);
    }
}
