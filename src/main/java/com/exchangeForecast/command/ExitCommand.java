package com.exchangeForecast.command;

public class ExitCommand extends Command {
    private static final String COMMAND_NAME = "exit";

    @Override
    public void execute() {
        System.out.println("GoodBye... See ya!");
        System.exit(0);
    }
}
