package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * No {@link Command}.
 */
public class NoCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "Я поддерживаю команды.\n"
            + "Чтобы посмотреть список команд введите help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {
        sendBotMessageService.sendMessage(NO_MESSAGE);
    }

}
