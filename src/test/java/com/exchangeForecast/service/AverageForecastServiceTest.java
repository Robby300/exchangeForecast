package com.exchangeForecast.service;

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
        rates.add(new Rate(LocalDate.now().minusDays(7), 9d, USD));
        rates.add(new Rate(LocalDate.now().minusDays(6), 10d, USD));
        rates.add(new Rate(LocalDate.now().minusDays(5), 11d, USD));
        rates.add(new Rate(LocalDate.now().minusDays(4), 12d, USD));
        rates.add(new Rate(LocalDate.now().minusDays(3), 13d, USD));
        rates.add(new Rate(LocalDate.now().minusDays(2), 14d, USD));
        rates.add(new Rate(LocalDate.now().minusDays(1), 15d, USD));
        //rates.add(new Rate(LocalDate.now(), 12, USD)) - nextDay;
        //rates.add(new Rate(LocalDate.now().plusDays(1), 12.43, USD)) - tomorrow;
        return rates;
    }

    @Test
    void shouldForecastNextDay() {

        Rate forecastRate = service.forecastNextDay(mockRates());
        Assertions.assertFalse(forecastRate == null);
        Assertions.assertEquals(forecastRate.getExchangeRate(), 12D);
        Assertions.assertEquals(forecastRate.getDate(), LocalDate.now());
        Assertions.assertEquals(forecastRate.getCurrency(), USD);
    }

    @Test
    void shouldForecastNextWeek() {
        List<Rate> forecastRates = service.forecastNextWeek(mockRates());
        Assertions.assertFalse(forecastRates == null);
    }

    @Test
    void shouldForecastTomorrow() {
        Rate forecastRate = service.forecastTomorrow(mockRates());
        Assertions.assertFalse(forecastRate == null);
        Assertions.assertEquals(forecastRate.getExchangeRate(), 12.43D);
        Assertions.assertEquals(forecastRate.getDate(), LocalDate.now().plusDays(1));
        Assertions.assertEquals(forecastRate.getCurrency(), USD);
    }
}