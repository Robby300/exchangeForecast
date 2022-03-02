package com.exchangeForecast.ui;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.Command;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.parser.CommandParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserInterface.class);
    private final RatesCash cash = new RatesCash();

     private final CommandParser commandParser = new CommandParser();

    public void initialize() {
        printUserInterface();
        while (true) {
            Command command = listenCommand();
            long before = System.nanoTime();
            command.execute(cash);
            long after = System.nanoTime();
            logger.info("время выполнение команды в нс = " + (after - before));
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
