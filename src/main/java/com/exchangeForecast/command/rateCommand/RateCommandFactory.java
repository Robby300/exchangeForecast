package com.exchangeForecast.command.rateCommand;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.rateCommand.RateCommand;
import com.exchangeForecast.command.rateCommand.RateCommandImpl;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.service.GraphOutputServiceImpl;
import com.exchangeForecast.service.LinearRegressionForecastService;
import com.exchangeForecast.service.ListOutputServiceImpl;
import com.exchangeForecast.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RateCommandFactory implements RateCommand{

    private final RatesCash cash;

    public RateCommandFactory(SendBotMessageService sendBotMessageService, RatesCash cash) {
        this.cash = cash;
    }

    public RateCommandImpl getCommand(Update update) {

        String[] textMessage = update.getMessage().getChatId().toString().split("\\s");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String cdx = textMessage[1];
        String period = textMessage[2];
        String date = textMessage[3];
        String alg = textMessage[5];
        String output = textMessage[7];

        RateCommandImpl rateCommand = new RateCommandImpl();

        rateCommand.setCdx(Currency.ofConsoleName(cdx));


    }

    @Override
    public void execute(Update update) {
        RateCommandImpl command = getCommand(update);
        if (command.getOutputMethod() instanceof ListOutputServiceImpl) {

        }
    }
}
