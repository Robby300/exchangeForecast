package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesInMemory;
import com.exchangeForecast.service.outputServcie.SendMessageService;
import com.google.common.collect.ImmutableMap;

import static com.exchangeForecast.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;


    public CommandContainer(SendMessageService sendMessageService, RatesInMemory cash) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendMessageService))
                .put(NO.getCommandName(), new NoCommand(sendMessageService))
                .put(RATE.getCommandName(), new RateCommand(sendMessageService, cash))
                .build();

        unknownCommand = new UnknownCommand(sendMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}