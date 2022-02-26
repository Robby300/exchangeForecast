package com.exchangeForecast.ui;

import com.exchangeForecast.command.Command;
import com.exchangeForecast.command.RateCommand;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.parsers.CommandParser;
import com.exchangeForecast.parsers.RatesParser;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;

import java.util.Optional;

public class UserInterface {
    private final ForecastService service = new AverageForecastService();
    final CommandParser commandParser = new CommandParser();
    private final RatesParser ratesParser = new RatesParser();

    public void initialize() {
        printUserInterface();
        while (true) {
            Command command = listenCommand();
            command.execute();
        }
    }

    public void printUserInterface() {
        System.out.println("Usage: rate <cdx> [period]");
        System.out.println("Usage: exit");
        System.out.println("    cdx:\n" +
                "       USD     <USD> [period]\n" +
                "       TRY     <TRY> [period]\n" +
                "       EUR     <EUR> [period]");
        System.out.println("    period:\n" +
                "       tomorrow    [tomorrow]\n" +
                "       week        [week]");
        System.out.println("Type your command");
    }

    public Command listenCommand() {
        Optional<Command> optionalCommand = commandParser.getCommand();
        return optionalCommand.orElseThrow(() -> new NotValidException("Not valid command"));
    }
}
