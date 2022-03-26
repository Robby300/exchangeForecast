package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stop {@link Command}.
 */
public class StopCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(StopCommand.class);
    private final SendMessageService sendMessageService;

    public static final String STOP_MESSAGE = "До свидания \uD83D\uDE1F.";

    public StopCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(String message) {
        sendMessageService.sendMessage(STOP_MESSAGE);
        logger.info("message: {}", message);
    }
}
