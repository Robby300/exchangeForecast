package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;
import com.exchangeForecast.service.LinearRegressionForecastService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class RateCommandWeek implements Command {

    private final Currency cdx;
    private final ForecastService service = new LinearRegressionForecastService();

    public RateCommandWeek(Currency cdx) {
        this.cdx = cdx;
    }

    public void execute(RatesCash cash) {
        List<Rate> ratesByCDX = cash.getRatesByCDX(cdx);
        List<Rate> rates = service.forecastNextWeek(ratesByCDX);
        rates.forEach(System.out::println);
    }

    @Override
    public void execute(Update update) {

    }
}
