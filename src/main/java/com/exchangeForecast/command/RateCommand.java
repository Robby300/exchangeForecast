package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesInMemory;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.domain.RateCommandParts;
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


    private final RateCommandPartsParser rateCommandPartsParser = new RateCommandPartsParser();

    private final SendBotMessageService sendBotMessageService;
    private final RatesInMemory cash;

    public RateCommand(SendBotMessageService sendBotMessageService, RatesInMemory cash) {
        this.cash = cash;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(String message) {

        RateCommandParts rateParts = rateCommandPartsParser.getRateCommandParts(message);
        List<List<Rate>> listsOfRates = rateParts.getAlgorithm().forecast(cash, rateParts.getCdx(), rateParts.getPeriod(), rateParts.getDate());
        logger.info("Данные спрогнозированны алгоритмом:  {}.", rateParts.getAlgorithm().getClass().getSimpleName());
        rateParts.getOutputMethod().output(sendBotMessageService, listsOfRates);
        logger.info("Данные направлены на вывод сервисом: {}.", rateParts.getOutputMethod().getClass().getSimpleName());
    }
}
