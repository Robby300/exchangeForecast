package com.exchangeForecast.parser;

import com.exchangeForecast.command.rateCommand.RateCommand;
import com.exchangeForecast.command.rateCommand.RateCommandTomorrow;
import com.exchangeForecast.command.rateCommand.RateCommandWeek;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.exceptions.NotValidException;
import org.telegram.telegrambots.meta.api.objects.Message;

public class RateCommandParser {

    /*public RateCommand getParseCommandFromMessage(Message message) {
        String[] commandParts = message.getText().split("\\s");
        Currency cdx = Currency.ofConsoleName(commandParts[1]);

        String period = commandParts[2];
        if (period.equals("week")) {
            command = new RateCommandWeek(sendBotMessageService, cdx, cash);
        }
        else if (period.equals("tomorrow")) {
            command = new RateCommandTomorrow(sendBotMessageService, cdx, cash);
        }
        else throw new NotValidException(period + " period not founded");
    }*/
}
