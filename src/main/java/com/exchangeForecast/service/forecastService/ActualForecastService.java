package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.RateNotFound;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ActualForecastService extends ForecastService {

    @Override
    public List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
        List<Rate> resultRates = new ArrayList<>();
        for (int i = 0; i < period.getDaysCount(); i++) {
            Rate rateTwoYearsAgo = rates.stream()
                    .filter(getRateAfterYearsAndDays(2, i))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Rate not founded"));
            Rate rateThreeYearsAgo = rates.stream()
                    .filter(getRateAfterYearsAndDays(3, i))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Rate not founded"));
            Rate forecastRate = Rate.builder().date(LocalDate.now().plusDays(1 + i)).currency(getLastRate(rates).getCurrency())
                    .exchangeRate(rateThreeYearsAgo.getExchangeRate().add(rateTwoYearsAgo.getExchangeRate()))
                    .build();
            resultRates.add(forecastRate);
        }
        return resultRates;
    }

    private Predicate<Rate> getRateAfterYearsAndDays(int years, int days) {
        return rate -> rate.getDate().isAfter(LocalDate.now().minusYears(years).plusDays(days));
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
