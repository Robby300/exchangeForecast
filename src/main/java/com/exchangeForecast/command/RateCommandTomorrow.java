package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;
import com.exchangeForecast.service.LinearRegressionForecastService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class RateCommandTomorrow implements Command {

    private final Currency cdx;
    //private final RatesCash ratesCash = new RatesCash();
    private final ForecastService service = new LinearRegressionForecastService();

    public RateCommandTomorrow(Currency cdx) {
        this.cdx = cdx;
    }

    public void execute(RatesCash cash) {
        List<Rate> ratesByCDX = cash.getRatesByCDX(cdx);
        System.out.println(service.forecastTomorrow(ratesByCDX));
    }

    @Override
    public void execute(Update update) {

    }
}
