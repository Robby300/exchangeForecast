package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.rateCommand.RateCommand;
import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import com.google.common.collect.ImmutableMap;

import static com.exchangeForecast.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;


    public CommandContainer(SendBotMessageService sendBotMessageService, RatesCash cash) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(RATE.getCommandName(), new RateCommand(sendBotMessageService, cash))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}