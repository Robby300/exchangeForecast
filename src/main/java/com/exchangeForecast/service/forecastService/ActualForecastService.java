package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.RateNotFound;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActualForecastService extends ForecastService {

    @Override
    public List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
        List<Rate> resultRates = new ArrayList<>();
        for (int i = 0; i < period.getDaysCount(); i++) {
            resultRates.add(forecastByDate(rates, LocalDate.now().plusDays(1 + i)));
        }
        return resultRates;
    }

    private Rate getRateFirstAfterDate(List<Rate> rates, LocalDate date) {
        return rates.stream()
                .filter(rate -> rate.getDate().isAfter(date))
                .findFirst()
                .orElseThrow(() -> new RateNotFound("Данных нет"));
    }

    @Override
    public Rate forecastByDate(List<Rate> rates, LocalDate date) {
        Rate rateTwoYearsAgo = rates.stream()
                .filter(rate -> rate.getDate().equals(date.minusYears(2)))
                .findFirst()
                .orElseGet(() -> getRateFirstAfterDate(rates, date.minusYears(2)));
        Rate rateThreeYearsAgo = rates.stream()
                .filter(rate -> rate.getDate().equals(date.minusYears(3)))
                .findFirst()
                .orElseGet(() -> getRateFirstAfterDate(rates, date.minusYears(3)));
        return Rate.builder()
                .date(date)
                .currency(getLastRate(rates).getCurrency())
                .exchangeRate(rateThreeYearsAgo.getExchangeRate().add(rateTwoYearsAgo.getExchangeRate()))
                .build();
    }
}
