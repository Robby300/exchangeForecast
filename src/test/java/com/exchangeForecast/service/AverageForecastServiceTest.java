package com.exchangeForecast.service;

import com.exchangeForecast.domain.Rate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.exchangeForecast.domain.Currency.USD;

class AverageForecastServiceTest {
    ForecastService service = new AverageForecastService();

    private List<Rate> mockRates() {
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate(LocalDate.now().minusDays(7), BigDecimal.valueOf(9), USD));
        rates.add(new Rate(LocalDate.now().minusDays(6), BigDecimal.valueOf(10d), USD));
        rates.add(new Rate(LocalDate.now().minusDays(5), BigDecimal.valueOf(11d), USD));
        rates.add(new Rate(LocalDate.now().minusDays(4), BigDecimal.valueOf(12d), USD));
        rates.add(new Rate(LocalDate.now().minusDays(3), BigDecimal.valueOf(13d), USD));
        rates.add(new Rate(LocalDate.now().minusDays(2), BigDecimal.valueOf(14d), USD));
        rates.add(new Rate(LocalDate.now().minusDays(1), BigDecimal.valueOf(15d), USD));
        //rates.add(new Rate(LocalDate.now(), 12, USD)) - nextDay;
        //rates.add(new Rate(LocalDate.now().plusDays(1), 12.43, USD)) - tomorrow;
        return rates;
    }

    @Test
    void shouldForecastNextDay() {

        Rate forecastRate = service.forecastNextDay(mockRates());
        Assertions.assertFalse(forecastRate == null);
        Assertions.assertEquals(forecastRate.getExchangeRate(), BigDecimal.valueOf(1200, 2));
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
        Assertions.assertEquals(forecastRate.getExchangeRate(), BigDecimal.valueOf(1243, 2));
        Assertions.assertEquals(forecastRate.getDate(), LocalDate.now().plusDays(1));
        Assertions.assertEquals(forecastRate.getCurrency(), USD);
    }
}