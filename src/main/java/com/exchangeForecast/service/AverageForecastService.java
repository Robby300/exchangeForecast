package com.exchangeForecast.service;

import com.exchangeForecast.domain.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class AverageForecastService implements ForecastService {
    private static final int SAMPLE_SIZE = 7;

    @Override
    public Rate forecastNextDay(List<Rate> rates) {
        Rate forecastRate;
        BigDecimal forecastExchangeRate;
        forecastExchangeRate = getLastWeekSubList(rates)
                .stream()
                .map(Rate::getExchangeRate)
                .reduce(BigDecimal::add).map(sum -> sum.divide(BigDecimal.valueOf(7), 2, RoundingMode.HALF_UP)).get();

        forecastRate = new Rate
                .Builder()
                .date(rates.get(rates.size() - 1).getDate().plusDays(1))
                .exchangeRate(forecastExchangeRate)
                .currency(rates.get(0).getCurrency())
                .build();
        return forecastRate;
    }

    @Override
    public Rate forecastTomorrow(List<Rate> rates) {
        Rate forecastRate = fillRatesWithForecastRate(notForecastRateReachedTomorrow, rates);
        return forecastRate;
    }

    @Override
    public List<Rate> forecastNextWeek(List<Rate> rates) {
        fillRatesWithForecastRate(notForecastRateReachedNextWeek, rates);
        return getLastWeekSubList(rates);
    }

    private Rate fillRatesWithForecastRate(Predicate<List<Rate>> predicate, List<Rate> rates) {
        Rate forecastRate = null;
        while (predicate.test(rates)) {
            List<Rate> lastWeekRates = getLastWeekSubList(rates);
            forecastRate = forecastNextDay(lastWeekRates);
            rates.add(forecastRate);
        }
        return forecastRate;
    }

    private final Predicate<List<Rate>> notForecastRateReachedTomorrow =
            forecastRates -> !forecastRates
                .get(forecastRates.size() - 1)
                .getDate()
                .equals(LocalDate.now().plusDays(1));

    private final Predicate<List<Rate>> notForecastRateReachedNextWeek =
            forecastRates -> !forecastRates
                .get(forecastRates.size() - 1)
                .getDate()
                .equals(LocalDate.now().plusDays(7));

    private List<Rate> getLastWeekSubList(List<Rate> rates) {
        return rates.subList(rates.size() - SAMPLE_SIZE, rates.size());
    }

}
