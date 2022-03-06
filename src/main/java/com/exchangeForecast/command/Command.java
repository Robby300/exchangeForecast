package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    void execute(Update update);
}
