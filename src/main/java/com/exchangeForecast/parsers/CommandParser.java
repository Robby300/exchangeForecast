package com.exchangeForecast.parsers;

import com.exchangeForecast.command.Command;
import com.exchangeForecast.command.CommandFactory;
import com.exchangeForecast.command.RateCommandWeek;
import com.exchangeForecast.command.ExitCommand;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.exceptions.NotValidException;

import java.util.Optional;
import java.util.Scanner;

public class CommandParser {
    private final Scanner scanner = new Scanner(System.in);
    private final CommandFactory factory = new CommandFactory();

    private String[] splitForPArts() {
        String command = scanner.nextLine();
        return command.split("\\s");
    }

    public Optional<Command> getCommand() {
        return factory.create(splitForPArts());
    }
}
