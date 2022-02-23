package com.exchangeForecast.ui;

import com.exchangeForecast.parsers.CommandParser;

public class UserInterface {
    private static final CommandParser commandParser = new CommandParser();

    public void printUserInterface() {
        System.out.println("Usage: rate <cdx> [period]");
        System.out.println("Usage: exit\n");
        System.out.println("    cdx:\n" +
                "       USD     <USD> [period]\n" +
                "       TRY     <TRY> [period]\n" +
                "       EUR     <EUR> [period]\n");
        System.out.println("    period:\n" +
                "       tomorrow    [tomorrow]\n" +
                "       week        [week]\n");
    }

    public void listenCommand() {
        commandParser.parse();
    }
}
