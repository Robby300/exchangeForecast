package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.parser.RateCommandPartsParser;
import com.exchangeForecast.service.forecastService.ForecastService;
import com.exchangeForecast.service.outputServcie.OutputService;
import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;

/**
 * Rate {@link Command}.
 */
@Setter
@Getter
public class RateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(RateCommand.class);
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
        RateCommandPartsParser rateCommandPartsParser = new RateCommandPartsParser();
        HashMap<String, Object> rateCommandParts = rateCommandPartsParser.getRateCommandParts(update);
        algorithm = (ForecastService) rateCommandParts.get("algorithm");
        cdx = (List<Currency>) rateCommandParts.get("cdx");
        period = (ForecastPeriod) rateCommandParts.get("period");
        date = (LocalDate) rateCommandParts.get("date");
        outputMethod = (OutputService) rateCommandParts.get("outputMethod");

        List<List<Rate>> listsOfRates = algorithm.forecast(cash, cdx, period, date);
        logger.info("Данные спрогнозированны алгоритмом:  {}.", algorithm.getClass().getSimpleName());
        outputMethod.output(update, sendBotMessageService, listsOfRates);
        logger.info("Данные направлены на вывод сервисом: {}.", outputMethod.getClass().getSimpleName());
    }


}
