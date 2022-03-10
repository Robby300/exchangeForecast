package com.exchangeForecast.service.algorithmService;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ActualForecastService implements ForecastService {

    private List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
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

    private List<Rate> forecastByDate(List<Rate> rates, LocalDate date) {
        Rate rateTwoYearsAgo = rates.stream()
                .filter(getRateAfterYearsAndDays(2, 0))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rate not founded"));
        Rate rateThreeYearsAgo = rates.stream()
                .filter(getRateAfterYearsAndDays(3, 0))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Rate not founded"));
        Rate forecastRate = Rate.builder().date(date).currency(getLastRate(rates).getCurrency())
                .exchangeRate(rateThreeYearsAgo.getExchangeRate().add(rateTwoYearsAgo.getExchangeRate()))
                .build();
        List<Rate> resultRates = new ArrayList<>();
        resultRates.add(forecastRate);
        return resultRates;
    }

    private Rate getLastRate(List<Rate> rates) {
        return rates.get(rates.size() - 1);
    }

    @Override
    public List<Rate> forecast(RatesCash cash, Currency cdx, ForecastPeriod period, LocalDate date) {
        List<Rate> rates = cash.getRatesByCDX(cdx);
        if (period != null) {
            return forecastByPeriod(rates, period);
        } else if (date != null) {
            return forecastByDate(rates, date);
        }
        return rates;
    }
}
