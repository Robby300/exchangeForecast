package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * No {@link Command}.
 */
public class NoCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(NoCommand.class);
    private final SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "Я поддерживаю команды.\n"
            + "Чтобы посмотреть список команд введите help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {
        sendBotMessageService.sendMessage(NO_MESSAGE);
        logger.info("message: {}", message);
    }

}
