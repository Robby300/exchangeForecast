package com.exchangeForecast.command.rateCommand;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.service.ForecastService;
import com.exchangeForecast.service.OutputService;
import com.exchangeForecast.service.SendBotMessageService;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

@Builder
public class RateCommandImpl implements RateCommand {
    private final Currency cdx;
    private final String period;
    private final LocalDate date;
    private final ForecastService algorithm;
    private final OutputService outputMethod;

    private final SendBotMessageService sendBotMessageService;
    private final RatesCash cash;

    @Builder
    public RateCommandImpl(Currency cdx, String period, LocalDate date,
                           ForecastService algorithm, OutputService outputMethod, SendBotMessageService sendBotMessageService, RatesCash cash) {
        this.cdx = cdx;
        this.period = period;
        this.date = date;
        this.algorithm = algorithm;
        this.outputMethod = outputMethod;
        this.sendBotMessageService = sendBotMessageService;
        this.cash = cash;
    }

    @Override
    public void execute(Update update) {

    }
}
