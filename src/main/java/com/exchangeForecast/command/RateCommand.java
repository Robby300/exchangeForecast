package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.forecastService.ActualForecastService;
import com.exchangeForecast.service.forecastService.ForecastService;
import com.exchangeForecast.service.forecastService.LinearRegressionForecastService;
import com.exchangeForecast.service.forecastService.MoonForecastService;
import com.exchangeForecast.service.outputServcie.GraphOutputService;
import com.exchangeForecast.service.outputServcie.ListOutputService;
import com.exchangeForecast.service.outputServcie.OutputService;
import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class RateCommand implements Command {
    private List<Currency> cdx;
    private ForecastPeriod period;
    private LocalDate date;
    private ForecastService algorithm;
    private OutputService outputMethod;
    private final SendBotMessageService sendBotMessageService;
    private final RatesCash cash;

    public RateCommand(SendBotMessageService sendBotMessageService, RatesCash cash) {
        this.cash = cash;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String[] messageArgs = update.getMessage().getText().split("\\s");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String cdxArgument = messageArgs[1];
        String timeLine = messageArgs[2];
        String timeLineArgument = messageArgs[3];
        String alg = messageArgs[4];
        String algArgument = messageArgs[5];
        String output = messageArgs[6];
        String outputArgument = messageArgs[7];

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
                    setOutputMethod(new GraphOutputService());
            }
        }
        List<List<Rate>> listsOfRates = algorithm.forecast(cash, cdx, period, date);
        try {
            outputMethod.output(update, sendBotMessageService, listsOfRates);
        } catch (PythonExecutionException | IOException e) {
            e.printStackTrace();
        }
    }
}
