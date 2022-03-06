package com.exchangeForecast.command;

public enum CommandName {
    START("start"),
    HELP("help"),
    NO("no"),
    STOP("stop"),
    RATE("rate"),
    CDX("cdx");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}