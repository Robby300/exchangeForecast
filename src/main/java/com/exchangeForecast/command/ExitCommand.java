package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;

public class ExitCommand implements Command {

    @Override
    public void execute(RatesCash cash) {
        System.out.println("GoodBye... See ya!");
        System.exit(0);
    }

}
