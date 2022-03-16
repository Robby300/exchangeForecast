package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command {

    public static final String UNKNOWN_MESSAGE = "Не понимаю вас \uD83D\uDE1F, напишите help чтобы узнать что я понимаю.";

    private final SendBotMessageService sendBotMessageService;

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {
        sendBotMessageService.sendMessage(UNKNOWN_MESSAGE);
    }
}
