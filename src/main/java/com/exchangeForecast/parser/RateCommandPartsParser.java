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
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class RateCommandPartsParser {
    private List<Currency> cdx;
    private ForecastPeriod period;
    private LocalDate date;
    private ForecastService algorithm;
    private OutputService outputMethod;

    public HashMap<String, Object> getRateCommandParts(Update update) {
        HashMap<String, Object> partsOfCommandInMap = new HashMap<>();
        String[] messageArgs = update.getMessage().getText().split("\\s");
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
            setOutputMethod(new ListOutputService());
        }
        String[] cdxLines = cdxArgument.split(",", 5);
        setCdx(Arrays.stream(cdxLines).map(Currency::ofConsoleName).collect(Collectors.toList()));
        switch (timeLine) {
            case "-period":
                setPeriod(ForecastPeriod.ofName(timeLineArgument));
                setDate(null);
                break;
            case "-date":
                if (timeLineArgument.equals("tomorrow")) {
                    setDate(LocalDate.now().plusDays(1));
                } else setDate(LocalDate.parse(timeLineArgument, formatter));
                setPeriod(null);
        }
        if (alg.equals("-alg")) {
            switch (algArgument) {
                case "linear":
                    setAlgorithm(new LinearRegressionForecastService());
                    break;
                case "actual":
                    setAlgorithm(new ActualForecastService());
                    break;
                case "moon":
                    setAlgorithm(new MoonForecastService());
            }
        }
        if (output.equals("-output")) {
            switch (outputArgument) {
                case "list":
                    setOutputMethod(new ListOutputService());
                    break;
                case "graph":
                    if (date == null) {
                        setOutputMethod(new GraphOutputService());
                    } else throw new NotValidException("График строится только на периоде.");
            }
        }
        partsOfCommandInMap.put("algorithm", algorithm);
        partsOfCommandInMap.put("cdx", cdx);
        partsOfCommandInMap.put("period", period);
        partsOfCommandInMap.put("date", date);
        partsOfCommandInMap.put("outputMethod", outputMethod);
        return partsOfCommandInMap;
    }
}
