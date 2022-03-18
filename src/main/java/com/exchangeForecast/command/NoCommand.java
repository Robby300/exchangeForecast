package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * No {@link Command}.
 */
public class NoCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(NoCommand.class);
    private final SendMessageService sendMessageService;

    public static final String NO_MESSAGE = "Я поддерживаю команды.\n"
            + "Чтобы посмотреть список команд введите help";

    public NoCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(String message) {
        sendMessageService.sendMessage(NO_MESSAGE);
        logger.info("message: {}", message);
    }

}
