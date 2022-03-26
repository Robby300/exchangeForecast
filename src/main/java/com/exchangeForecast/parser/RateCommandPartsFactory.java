package com.exchangeForecast.parser;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.RateCommandParts;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.service.forecastService.ActualForecastService;
import com.exchangeForecast.service.forecastService.LinearRegressionForecastService;
import com.exchangeForecast.service.forecastService.MoonForecastService;
import com.exchangeForecast.service.outputServcie.GraphOutputMethod;
import com.exchangeForecast.service.outputServcie.ListOutputMethod;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public class RateCommandPartsFactory {
    private static final Logger logger = LoggerFactory.getLogger(RateCommandPartsFactory.class);

    public RateCommandParts getRateCommandParts(String message) {
        RateCommandParts parts = new RateCommandParts();
        String[] messageArgs = message.split("\\s");
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
            parts.setOutputMethod(new ListOutputMethod());
        }
        String[] cdxLines = cdxArgument.split(",", 5);
        parts.setCdx(Arrays.stream(cdxLines).map(Currency::ofConsoleName).collect(Collectors.toList()));
        switch (timeLine) {
            case "-period":
                parts.setPeriod(ForecastPeriod.ofName(timeLineArgument));
                break;
            case "-date":
                if (timeLineArgument.equals("tomorrow")) {
                    parts.setDate(LocalDate.now().plusDays(1));
                } else {
                    parts.setDate(LocalDate.parse(timeLineArgument, formatter));
                }
        }
        if (alg.equals("-alg")) {
            switch (algArgument) {
                case "linear":
                    parts.setAlgorithm(new LinearRegressionForecastService());
                    break;
                case "actual":
                    parts.setAlgorithm(new ActualForecastService());
                    break;
                case "moon":
                    parts.setAlgorithm(new MoonForecastService());
            }
        }
        if (output.equals("-output")) {
            switch (outputArgument) {
                case "list":
                    parts.setOutputMethod(new ListOutputMethod());
                    break;
                case "graph":
                    if (parts.getDate() == null) {
                        parts.setOutputMethod(new GraphOutputMethod());
                    } else {
                        logger.info("График строится только на периоде.");
                        throw new NotValidException("График строится только на периоде.");
                    }
            }
        }
        return parts;
    }
}
