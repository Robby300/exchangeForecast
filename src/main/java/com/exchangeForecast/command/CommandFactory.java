package com.exchangeForecast.command;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.exceptions.NotValidException;

import java.util.Optional;

public class CommandFactory {

    public Optional<Command> create(String[] commandParts) {
        Command command;
        String commandName = commandParts[0];
        if (commandName.equals("exit")) {
            command = new ExitCommand();
        } else if (commandName.equals("rate")) {
            command = getRateCommand(commandParts);
        }
        else throw new NotValidException(commandName + " commandName not founded");
        return Optional.of(command);
    }

    private Command getRateCommand(String[] commandParts) {
        Command command;
        Currency cdx = Currency.ofConsoleName(commandParts[1]);
        String period = commandParts[2];
        if (period.equals("week")) {
            command = new RateCommandWeek(cdx);
        }
        else if (period.equals("tomorrow")) {
            command = new RateCommandTomorrow(cdx);
        }
        else throw new NotValidException(period + " period not founded");
        return command;
    }
}
