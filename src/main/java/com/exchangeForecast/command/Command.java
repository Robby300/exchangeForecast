package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;

public interface Command {
    public abstract void execute(RatesCash cash);
}
