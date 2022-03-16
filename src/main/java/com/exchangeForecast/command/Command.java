package com.exchangeForecast.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    void execute(String message);
}
