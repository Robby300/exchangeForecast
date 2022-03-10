package com.exchangeForecast.service.algorithmService;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MysticForecastService implements ForecastService {
    private final List<LocalDate> threeLastFullMoons = List.of(
            LocalDate.of(2022, 1, 18),
            LocalDate.of(2022, 2, 16),
            LocalDate.of(2021, 12, 19)
    );

    @Override
    public List<Rate> forecast(RatesCash cash, Currency cdx, ForecastPeriod period, LocalDate date) {
        List<Rate> rates = cash.getRatesByCDX(cdx);
        if (period != null) {
            return forecastByPeriod(rates, period);
        } else if (date != null) {
            return List.of(forecastByDate(rates, date));
        }
        return rates;
    }

    private List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
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
                .filter(rate -> rate.getDate().equals(date))
                .findFirst()
                .orElse(getRateNearByDate(rates, date));
    }

    private Rate getRateNearByDate(List<Rate> rates, LocalDate date) {
        return rates.stream()
                .filter(rate -> rate.getDate().isAfter(date))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Данных после полнолуния нет"));
    }

    private Rate forecastByDate(List<Rate> rates, LocalDate date) {
        BigDecimal sumOfThreeRatesOfFullMoon = new BigDecimal(0);
        for (LocalDate localDate : threeLastFullMoons) {
            sumOfThreeRatesOfFullMoon = sumOfThreeRatesOfFullMoon.add(getRateByDate(rates, localDate).getExchangeRate());
        }
        BigDecimal forecastRate = sumOfThreeRatesOfFullMoon.divide(BigDecimal.valueOf(3), RoundingMode.HALF_DOWN);
        return Rate.builder()
                .date(date)
                .exchangeRate(forecastRate)
                .currency(getLastRate(rates).getCurrency())
                .build();
    }

    private Rate getLastRate(List<Rate> rates) {
        return rates.get(rates.size() - 1);
    }
}
