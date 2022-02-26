package com.exchangeForecast.command;

public class Process {

    public void process(RateCommand command) {

        command.execute();
        // Здесь мы могли бы забрать и сохранить платежные данные из стратегии.
    }
}
