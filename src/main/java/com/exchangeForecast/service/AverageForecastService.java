package com.exchangeForecast.service;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;

import java.time.LocalDate;
import java.util.List;

public class AverageForecastService implements ForecastService {
    private static final int SAMPLE_SIZE = 7;

    @Override
    public Rate forecastTomorrow(List<Rate> rates) {
        Rate forecastRate;
        Double forecastExchangeRate = getLastWeekSubList(rates).stream()
                .map(Rate::getExchangeRate)
                .reduce(Double::sum).map(sum -> sum / SAMPLE_SIZE).get();

         forecastRate = new Rate.Builder()
                .date(LocalDate.now().plusDays(1))
                .exchangeRate(forecastExchangeRate)
                .currency(Currency.EUR)
                .build();

        return forecastRate;
    }

    @Override
    public List<Rate> forecastNextWeek(List<Rate> rates) {
        List<Rate> forecastRates = getLastWeekSubList(rates);
        for (int i = 0; i < 7; i++) {
            Rate forecastRate = forecastTomorrow(forecastRates);
            forecastRate.setDate(forecastRate.getDate().plusDays(i));
            forecastRates.remove(0);
            forecastRates.add(forecastRate);
        }
        return forecastRates;
    }

    private List<Rate> getLastWeekSubList(List<Rate> rates) {
        return rates.subList(rates.size() - SAMPLE_SIZE, rates.size());
    }
}
