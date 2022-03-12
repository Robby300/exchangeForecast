package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.RateNotFound;
import com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoonForecastService extends ForecastService {
    private final List<LocalDate> threeLastFullMoons = ImmutableList.of(
            LocalDate.of(2022, 1, 18),
            LocalDate.of(2022, 2, 16),
            LocalDate.of(2021, 12, 19)
    );

    public List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
        List<Rate> resultRates = new ArrayList<>();
        Rate tomorrowRate = forecastByDate(rates, LocalDate.now().plusDays(1));
        resultRates.add(tomorrowRate);
        for (int i = 1; i < period.getDaysCount(); i++) {
            Rate newForecastRate = Rate.builder()
                    .date(tomorrowRate.getDate().plusDays(1))
                    .exchangeRate(resultRates.get(resultRates.size() - 1)
                            .getExchangeRate().multiply(getRandom(0.9, 1.1)))
                    .currency(getLastRate(rates).getCurrency())
                    .build();
            resultRates.add(newForecastRate);
        }
        return resultRates;
    }

    private BigDecimal getRandom(double min, double max) {
        double random = Math.random();      // random     == 0.52796 (for example)
        double range = max - min;           // range      == 0.2
        double adjustment = range * random; // adjustment == 0.105592
        return new BigDecimal(min + adjustment);
    }

    private Rate getRateByDate(List<Rate> rates, LocalDate date) {
        return rates.stream()
                .filter(rate -> rate.getDate().isEqual(date))
                .findFirst()
                .orElseGet(() -> getRateFirstAfterDate(rates, date));
    }

    private Rate getRateFirstAfterDate(List<Rate> rates, LocalDate date) {
        return rates.stream()
                .filter(rate -> rate.getDate().isAfter(date))
                .findFirst()
                .orElseThrow(() -> new RateNotFound("Данных после полнолуния нет"));
    }

    public Rate forecastByDate(List<Rate> rates, LocalDate date) {
        BigDecimal sumOfThreeRatesOfFullMoon = new BigDecimal(0);
        for (LocalDate localDate : threeLastFullMoons) {
            sumOfThreeRatesOfFullMoon = sumOfThreeRatesOfFullMoon
                    .add(getRateByDate(rates, localDate).getExchangeRate());
        }
        BigDecimal forecastRate = sumOfThreeRatesOfFullMoon.divide(BigDecimal.valueOf(3), RoundingMode.HALF_DOWN);
        return Rate.builder()
                .date(date)
                .exchangeRate(forecastRate)
                .currency(getLastRate(rates).getCurrency())
                .build();
    }
}
