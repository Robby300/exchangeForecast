package com.exchangeForecast.service;

import com.exchangeForecast.domain.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class AverageForecastService implements ForecastService {
    private static final int SAMPLE_SIZE = 7;

    @Override
    public Rate forecastNextDay(List<Rate> rates) {
        Rate forecastRate;
        BigDecimal forecastExchangeRate = getLastWeekSubList(rates)
                .stream()
                .map(Rate::getExchangeRate)
                .reduce(BigDecimal::add).map(sum -> sum.divide(BigDecimal.valueOf(7), 2, RoundingMode.HALF_UP)).get();

        forecastRate = new Rate.Builder()
                .date(rates.get(rates.size() - 1).getDate().plusDays(1))
                .exchangeRate(forecastExchangeRate)
                .currency(rates.get(0).getCurrency())
                .build();
        return forecastRate;
    }

    @Override
    public List<Rate> forecastNextWeek(List<Rate> rates) {
        List<Rate> forecastRates = getLastWeekSubList(rates);
        while (notForecastRatesReachedTomorrow(forecastRates)) {
            Rate forecastRate = forecastNextDay(forecastRates);
            forecastRates.remove(0);
            forecastRates.add(forecastRate);
        }
        return forecastRates;
    }

    private boolean notForecastRatesReachedTomorrow(List<Rate> forecastRates) {
        return !forecastRates.get(0).getDate().equals(LocalDate.now().plusDays(1));
    }

    private boolean notForecastRateReachedTomorrow(Rate forecastRate) {
        return !forecastRate.getDate().equals(LocalDate.now().plusDays(1));
    }

    private List<Rate> getLastWeekSubList(List<Rate> rates) {
        return rates.subList(rates.size() - SAMPLE_SIZE, rates.size());
    }
     @Override
    public Rate forecastTomorrow(List<Rate> rates) {
        List<Rate> forecastRates = getLastWeekSubList(rates);
        Rate forecastRate = forecastRates.get(SAMPLE_SIZE - 1);
        while (notForecastRateReachedTomorrow(forecastRate)) {
            forecastRate = forecastNextDay(forecastRates);
            forecastRates.remove(0);
            forecastRates.add(forecastRate);
        }
        return forecastRate;
    }
}
