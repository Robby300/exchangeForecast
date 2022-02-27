package com.exchangeForecast.command;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;

import java.util.List;

public class RateCommandTomorrow extends Command {
    private static final String COMMAND_NAME = "rate";
    private static final String PERIOD = "tomorrow";

    private final Currency cdx;
    private final RatesCash ratesCash = new RatesCash();
    private final ForecastService service = new AverageForecastService();

    public RateCommandTomorrow(Currency cdx) {
        this.cdx = cdx;
    }

    @Override
    public void execute() {
        List<Rate> ratesByCDX = ratesCash.getRatesByCDX(cdx);
        System.out.println(service.forecastTomorrow(ratesByCDX));
    }
}
