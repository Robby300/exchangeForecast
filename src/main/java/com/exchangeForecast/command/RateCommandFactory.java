package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.rateCommand.RateCommand;
import com.exchangeForecast.parser.RateCommandParser;
import com.exchangeForecast.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RateCommandFactory {

    private final SendBotMessageService sendBotMessageService;
    private final RatesCash cash;

    private RateCommandParser parser = new RateCommandParser();

    public RateCommandFactory(SendBotMessageService sendBotMessageService, RatesCash cash) {
        this.sendBotMessageService = sendBotMessageService;
        this.cash = cash;
    }

    public RateCommand getCommand() {

    }


}
