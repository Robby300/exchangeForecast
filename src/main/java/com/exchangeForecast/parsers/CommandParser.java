package com.exchangeForecast.parsers;

import com.exchangeForecast.domain.Command;

import java.util.Optional;
import java.util.Scanner;

public class CommandParser {
    private String firstCommand;
    private String cdx;
    private String period;
    private Scanner scanner = new Scanner(System.in);

    private String[] splitForPArts() {
        String command = scanner.nextLine();
        String[] commandParts = command.split("\\s");
        return commandParts;
    }

    public Optional<Command> getCommand() {
        Command command = null;
        String[] commandParts = splitForPArts();
        if (commandParts.length == 1) {
            String firstCommand = commandParts[0];
            command = new Command(firstCommand);
        } else if (commandParts.length == 3) {
            String firstCommand = commandParts[0];
            String cdx = commandParts[1];
            String period = commandParts[2];
            command = new Command(firstCommand, cdx, period);
        }
        return Optional.of(command);
    }

}
