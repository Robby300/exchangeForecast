package com.exchangeForecast.command.rateCommand;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.Command;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.algorithmService.ActualForecastService;
import com.exchangeForecast.service.algorithmService.ForecastService;
import com.exchangeForecast.service.algorithmService.LinearRegressionForecastService;
import com.exchangeForecast.service.algorithmService.MysticForecastService;
import com.exchangeForecast.service.outputServcie.GraphOutputService;
import com.exchangeForecast.service.outputServcie.ListOutputService;
import com.exchangeForecast.service.outputServcie.OutputService;
import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
@Getter

public class RateCommand implements Command {
    private Currency cdx;
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

        setCdx(Currency.ofConsoleName(cdxArgument));
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
                case  "mystic":
                    setAlgorithm(new MysticForecastService());
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
        List<Rate> rates = algorithm.forecast(cash, cdx, period, date);
        outputMethod.output(update, sendBotMessageService, rates);
    }
}
