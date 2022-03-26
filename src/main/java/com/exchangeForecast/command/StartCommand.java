package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(StartCommand.class);
    private final SendMessageService sendMessageService;

    public final static String START_MESSAGE = "Привет. Я bot. Я буду прогнозировать курсы валют, " +
            "Я еще маленький и только учусь.";

    public StartCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(String message) {
        sendMessageService.sendMessage(START_MESSAGE);
        logger.info("message: {}", message);
    }
}
