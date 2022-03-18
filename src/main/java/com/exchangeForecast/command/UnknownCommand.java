package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnknownCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UnknownCommand.class);

    public static final String UNKNOWN_MESSAGE = "Не понимаю вас \uD83D\uDE1F, напишите help чтобы узнать что я понимаю.";

    private final SendMessageService sendMessageService;

    public UnknownCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(String message) {
        sendMessageService.sendMessage(UNKNOWN_MESSAGE);
        logger.info("message: {}", message);
    }
}
