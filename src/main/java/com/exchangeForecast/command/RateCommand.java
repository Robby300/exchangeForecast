package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCache;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.parser.RateCommandPartsParser;
import com.exchangeForecast.service.forecastService.ForecastService;
import com.exchangeForecast.service.outputServcie.OutputService;
import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
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
    private final RatesCache cash;

    public RateCommand(SendBotMessageService sendBotMessageService, RatesCache cash) {
        this.cash = cash;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        RateCommandPartsParser rateCommandPartsParser = new RateCommandPartsParser(update);
        rateCommandPartsParser.extractRateCommandParts();
        algorithm = rateCommandPartsParser.getAlgorithm();
        cdx = rateCommandPartsParser.getCdx();
        period = rateCommandPartsParser.getPeriod();
        date = rateCommandPartsParser.getDate();
        outputMethod = rateCommandPartsParser.getOutputMethod();

        List<List<Rate>> listsOfRates = algorithm.forecast(cash, cdx, period, date);
        logger.info("Данные спрогнозированны алгоритмом:  {}.", algorithm.getClass().getSimpleName());
        outputMethod.output(update, sendBotMessageService, listsOfRates);
        logger.info("Данные направлены на вывод сервисом: {}.", outputMethod.getClass().getSimpleName());
    }

}
