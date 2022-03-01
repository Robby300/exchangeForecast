package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;

import java.util.List;

public class RateCommandWeek implements Command {

    private final Currency cdx;
    private final ForecastService service = new AverageForecastService();

    public RateCommandWeek(Currency cdx) {
        this.cdx = cdx;
    }

    @Override
    public void execute(RatesCash cash) {
        List<Rate> ratesByCDX = cash.getRatesByCDX(cdx);
        List<Rate> rates = service.forecastNextWeek(ratesByCDX);
        rates.forEach(System.out::println);
    }
}
