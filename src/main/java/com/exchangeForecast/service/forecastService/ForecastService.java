package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class ForecastService {

    abstract Rate forecastByDate(List<Rate> rates, LocalDate date);

    abstract List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period);

    public List<List<Rate>> forecast(RatesCash cash, List<Currency> cdx, ForecastPeriod period, LocalDate date) {
        List<List<Rate>> listsOfRates = new ArrayList<>();
        for (Currency c : cdx) {
            List<Rate> rates = cash.getRatesByCDX(c);
            if (period != null) {
                listsOfRates.add(forecastByPeriod(rates, period));
            } else if (date != null) {
                listsOfRates.add(List.of(forecastByDate(rates, date)));
            }
        }
        return listsOfRates;
    }

    Rate getLastRate(List<Rate> rates) {
        return rates.get(rates.size() - 1);
    }
}
