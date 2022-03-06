package com.exchangeForecast.command.rateCommand;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.command.Command;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.ForecastService;
import com.exchangeForecast.service.LinearRegressionForecastService;
import com.exchangeForecast.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class RateCommandWeek implements Command{

    private final Currency cdx;
    private final SendBotMessageService sendBotMessageService;
    private final ForecastService service = new LinearRegressionForecastService();
    private final RatesCash cash;

    public RateCommandWeek(SendBotMessageService sendBotMessageService,
                           Currency cdx, RatesCash cash) {
        this.cdx = cdx;
        this.sendBotMessageService = sendBotMessageService;
        this.cash = cash;
    }

    private String ratesToString(List<Rate> rates) {
        String result = "";
        for (Rate rate :
                rates) {
            result += rate.toString() + "\n";
        }
        return result;
    }

    public void execute(Update update) {
        List<Rate> ratesByCDX = cash.getRatesByCDX(cdx);
        List<Rate> rates = service.forecastNextWeek(ratesByCDX);
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), ratesToString(rates));
    }

}
