package com.exchangeForecast.command.rateCommand;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.service.ForecastService;
import com.exchangeForecast.service.OutputService;
import com.exchangeForecast.service.SendBotMessageService;
import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RateCommandImpl implements RateCommand {
    private Currency cdx;
    private ForecastPeriod period;
    private LocalDate date;
    private ForecastService algorithm;
    private OutputService outputMethod;
    private SendBotMessageService sendBotMessageService;
    private RatesCash cash;

    @Override
    public void execute(Update update) {

    }
}
