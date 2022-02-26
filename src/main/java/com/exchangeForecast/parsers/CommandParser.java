package com.exchangeForecast.parsers;

import com.exchangeForecast.command.Command;
import com.exchangeForecast.command.RateCommand;
import com.exchangeForecast.command.ExitCommand;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.exceptions.NotValidException;

import java.util.Optional;
import java.util.Scanner;

public class CommandParser {
    private final Scanner scanner = new Scanner(System.in);

    private String[] splitForPArts() {
        String command = scanner.nextLine();
        return command.split("\\s");
    }

    public Optional<Command> getCommand() {
        Command command = null;
        String[] commandParts = splitForPArts();
        String commandName = commandParts[0];
        if (commandName.equals("exit")) {
            command = new ExitCommand();
        } else if (commandName.equals("rate")) {
            String cdx = commandParts[1];
            String period = commandParts[2];
            command = new RateCommand.Builder()
                    .cdx(Currency.ofConsoleName(cdx))
                    .period(period)
                    .build();
        } else throw new NotValidException(commandName + "commandName not founded");
        return Optional.of(command);
    }
}
