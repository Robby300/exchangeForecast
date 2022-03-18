package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesInMemory;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.domain.RateCommandParts;
import com.exchangeForecast.parser.RateCommandPartsFactory;
import com.exchangeForecast.service.outputServcie.OutputMethod;
import com.exchangeForecast.service.outputServcie.SendMessageService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Rate {@link Command}.
 */
@Setter
@Getter
public class RateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(RateCommand.class);

    private final RateCommandPartsFactory rateCommandPartsFactory = new RateCommandPartsFactory();
    private final SendMessageService sendMessageService;
    private final RatesInMemory cash;

    public RateCommand(SendMessageService sendMessageService, RatesInMemory cash) {
        this.cash = cash;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(String message) {
        RateCommandParts rateParts = rateCommandPartsFactory.getRateCommandParts(message);
        OutputMethod method = rateParts.getOutputMethod();
        List<List<Rate>> listsOfRates = rateParts.getAlgorithm().forecast(cash, rateParts.getCdx(), rateParts.getPeriod(), rateParts.getDate());
        logger.info("Данные спрогнозированны алгоритмом:  {}.", rateParts.getAlgorithm().getClass().getSimpleName());
        method.output(sendMessageService, listsOfRates);
        logger.info("Данные направлены на вывод сервисом: {}.", rateParts.getOutputMethod().getClass().getSimpleName());
    }
}
