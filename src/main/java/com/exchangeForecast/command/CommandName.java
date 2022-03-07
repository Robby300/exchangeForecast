package com.exchangeForecast.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandName {
    START("start"),
    HELP("help"),
    NO("no"),
    STOP("stop"),
    RATE("rate");

    private final String commandName;
}