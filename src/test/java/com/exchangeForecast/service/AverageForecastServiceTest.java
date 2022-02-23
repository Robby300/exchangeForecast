package com.exchangeForecast.service;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.exchangeForecast.domain.Currency.USD;

class AverageForecastServiceTest {
    ForecastService service = new AverageForecastService();

    private List<Rate> mockRates() {
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate(LocalDate.now().minusDays(6), 10, USD));
        rates.add(new Rate(LocalDate.now().minusDays(5), 11, USD));
        rates.add(new Rate(LocalDate.now().minusDays(4), 12, USD));
        rates.add(new Rate(LocalDate.now().minusDays(3), 13, USD));
        rates.add(new Rate(LocalDate.now().minusDays(2), 14, USD));
        rates.add(new Rate(LocalDate.now().minusDays(1), 15, USD));
        rates.add(new Rate(LocalDate.now(), 16, USD));
        return rates;
    }

    @Test
    void shouldForecastNextDay() {

        Rate forecastRate = service.forecastNextDay(mockRates());
        Assertions.assertFalse(forecastRate == null);
        Assertions.assertEquals(forecastRate.getExchangeRate(), 13D);
        Assertions.assertEquals(forecastRate.getDate(), LocalDate.now().plusDays(1));
        Assertions.assertEquals(forecastRate.getCurrency(), USD);
    }

    @Test
    void shouldForecastNextWeek() {
        service.forecastNextWeek(mockRates());
    }

    @Test
    void shouldForecastTomorrow() {
        service.forecastTomorrow(mockRates());
    }
}