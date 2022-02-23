package com.exchangeForecast.ui;

import com.exchangeForecast.domain.Command;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.links.ExchangeRateLinks;
import com.exchangeForecast.parsers.CommandParser;
import com.exchangeForecast.parsers.RatesParser;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;

import java.util.List;
import java.util.Optional;

public class UserInterface {
    private final ForecastService service = new AverageForecastService();
    public final ExchangeRateLinks links = new ExchangeRateLinks();
    final CommandParser commandParser = new CommandParser();
    private final RatesParser ratesParser = new RatesParser();

    public void initialize() {
        while (true) {
            printUserInterface();
            listenCommand();
        }
    }

    public void printUserInterface() {
        System.out.println("Usage: rate <cdx> [period]");
        System.out.println("Usage: exit");
        System.out.println("    cdx:\n" +
                "       USD     <USD> [period]\n" +
                "       TRY     <TRY> [period]\n" +
                "       EUR     <EUR> [period]\n");
        System.out.println("    period:\n" +
                "       tomorrow    [tomorrow]\n" +
                "       week        [week]");
    }

    public void listenCommand() {
        Optional<Command> optionalCommand = commandParser.getCommand();
        if (optionalCommand.isPresent()) {
            try {
                Command command = optionalCommand.get();
                doCommand(command);
            } catch (NullPointerException e) {
                System.err.println("Wrong command");
            }
        }
    }

    private void doCommand(Command command) {
        doFirstPartCommand(command);
        List<Rate> ratesByCDX = doSecondPartCommand(command);
        doThirdPartCommand(command, ratesByCDX);
    }

    private void doFirstPartCommand(Command command) {
        if (command.getFirstCommand().equals("exit")) {
            System.out.println("GoodBye... See ya!");
            System.exit(0);
        } 
    }

    private List<Rate> doSecondPartCommand(Command command) {
        String cdx = command.getCdx();
        List<Rate> ratesByCDX = ratesParser.getRatesByCDX(cdx);
        return ratesByCDX;
    }

    private void doThirdPartCommand(Command command, List<Rate> ratesByCDX) {
        String period = command.getPeriod();
        if (period.equals("tomorrow")) {
            System.out.println(service.forecastTomorrow(ratesByCDX));
        } else if (period.equals("week")) {
            List<Rate> rates = service.forecastNextWeek(ratesByCDX);
            rates.stream().forEach(System.out::println);
        } else {
            System.err.println("wrong period!");
        }
    }


}
