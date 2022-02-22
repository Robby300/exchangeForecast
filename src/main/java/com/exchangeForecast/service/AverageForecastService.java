package com.exchangeForecast.service;

import com.exchangeForecast.domens.Rate;

import java.util.List;
import java.util.Optional;

public class AverageForecastService implements ForecastService {
    private static final int SAMPLE_SIZE = 7;

    @Override
    public void forecastTomorrow(List<Rate> rates) {
        System.out.println(getLastWeekSubList(rates).stream()
                .map(Rate::getExchangeRate)
                .reduce(Double::sum).map(sum -> sum / SAMPLE_SIZE));
        System.out.println(rates.get(1));
    }

    private List<Rate> getLastWeekSubList(List<Rate> rates) {
        return rates.subList(rates.size() - SAMPLE_SIZE, rates.size());
    }
}
