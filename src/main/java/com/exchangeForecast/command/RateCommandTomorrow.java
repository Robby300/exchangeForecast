package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;

import java.util.List;

public class RateCommandTomorrow implements Command {

    private final Currency cdx;
    //private final RatesCash ratesCash = new RatesCash();
    private final ForecastService service = new AverageForecastService();

    public RateCommandTomorrow(Currency cdx) {
        this.cdx = cdx;
    }

    @Override
    public void execute(RatesCash cash) {
        List<Rate> ratesByCDX = cash.getRatesByCDX(cdx);
        System.out.println(service.forecastTomorrow(ratesByCDX));
    }
}
