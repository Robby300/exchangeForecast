package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stop {@link Command}.
 */
public class StopCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(StopCommand.class);
    private final SendBotMessageService sendBotMessageService;

    public static final String STOP_MESSAGE = "До свидания \uD83D\uDE1F.";

    public StopCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {
        sendBotMessageService.sendMessage(STOP_MESSAGE);
        logger.info("message: {}", message);
    }
}
