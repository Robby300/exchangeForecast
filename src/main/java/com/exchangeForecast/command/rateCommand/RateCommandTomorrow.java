package com.exchangeForecast.command.rateCommand;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.Command;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;
import com.exchangeForecast.service.LinearRegressionForecastService;
import com.exchangeForecast.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class RateCommandTomorrow implements Command {

    private final Currency cdx;
    private final ForecastService service = new LinearRegressionForecastService();
    private final SendBotMessageService sendBotMessageService;
    private final RatesCash cash;

    public RateCommandTomorrow(SendBotMessageService sendBotMessageService,
                           Currency cdx, RatesCash cash) {
        this.cdx = cdx;
        this.sendBotMessageService = sendBotMessageService;
        this.cash = cash;
    }

    public void execute(Update update) {
        List<Rate> ratesByCDX = cash.getRatesByCDX(cdx);
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), service.forecastTomorrow(ratesByCDX).toString());
    }
}
