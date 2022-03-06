package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.rateCommand.RateCommandTomorrow;
import com.exchangeForecast.command.rateCommand.RateCommandWeek;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RateCommandFactory implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final RatesCash cash = new RatesCash();

    public RateCommandFactory(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String[] commandParts = update.getMessage().getText().split(" ");
        Command command;
        Currency cdx = Currency.ofConsoleName(commandParts[1]);
        String period = commandParts[2];
        if (period.equals("week")) {
            command = new RateCommandWeek(sendBotMessageService, cdx, cash);
        }
        else if (period.equals("tomorrow")) {
            command = new RateCommandTomorrow(sendBotMessageService, cdx, cash);
        }
        else throw new NotValidException(period + " period not founded");
        command.execute(update);
    }
}
