package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(StartCommand.class);
    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Привет. Я bot. Я буду прогнозировать курсы валют, " +
            "Я еще маленький и только учусь.";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {
        sendBotMessageService.sendMessage(START_MESSAGE);
        logger.info("message: {}", message);
    }
}
