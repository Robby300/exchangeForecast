package com.exchangeForecast.parser;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.service.forecastService.ActualForecastService;
import com.exchangeForecast.service.forecastService.ForecastService;
import com.exchangeForecast.service.forecastService.LinearRegressionForecastService;
import com.exchangeForecast.service.forecastService.MoonForecastService;
import com.exchangeForecast.service.outputServcie.GraphOutputService;
import com.exchangeForecast.service.outputServcie.ListOutputService;
import com.exchangeForecast.service.outputServcie.OutputService;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RateCommandPartsParser {
    private List<Currency> cdx;
    private ForecastPeriod period;
    private LocalDate date;
    private ForecastService algorithm;
    private OutputService outputMethod;
    private final Update update;

    public RateCommandPartsParser(Update update) {
        this.update = update;
    }

    public void extractRateCommandParts() {
        String[] messageArgs = getMessageArgs();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String cdxArgument = messageArgs[1];
        String timeLine = messageArgs[2];
        String timeLineArgument = messageArgs[3];
        String alg = messageArgs[4];
        String algArgument = messageArgs[5];
        String output = "";
        String outputArgument = "";
        if (messageArgs.length > 6) {
            output = messageArgs[6];
            outputArgument = messageArgs[7];
        } else {
            outputMethod = new ListOutputService();
        }
        String[] cdxLines = cdxArgument.split(",", 5);
        cdx = Arrays.stream(cdxLines).map(Currency::ofConsoleName).collect(Collectors.toList());
        switch (timeLine) {
            case "-period":
                period = ForecastPeriod.ofName(timeLineArgument);
                date = null;
                break;
            case "-date":
                if (timeLineArgument.equals("tomorrow")) {
                    date = LocalDate.now().plusDays(1);
                } else {
                    date = LocalDate.parse(timeLineArgument, formatter);
                    period = null;
                }
        }
        if (alg.equals("-alg")) {
            switch (algArgument) {
                case "linear":
                    algorithm = new LinearRegressionForecastService();
                    break;
                case "actual":
                    algorithm = new ActualForecastService();
                    break;
                case "moon":
                    algorithm = new MoonForecastService();
            }
        }
        if (output.equals("-output")) {
            switch (outputArgument) {
                case "list":
                    outputMethod = new ListOutputService();
                    break;
                case "graph":
                    if (date == null) {
                        outputMethod = new GraphOutputService();
                    } else {
                        throw new NotValidException("График строится только на периоде.");
                    }
            }
        }
    }

    private String[] getMessageArgs() {
        String[] messageArgs = update.getMessage().getText().split("\\s");
        return messageArgs;
    }
}
