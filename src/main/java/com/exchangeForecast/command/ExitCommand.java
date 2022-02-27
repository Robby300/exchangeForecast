package com.exchangeForecast.command;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.out.println("GoodBye... See ya!");
        System.exit(0);
    }
}
