package com.exchangeForecast.service;

import com.exchangeForecast.domain.Rate;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.exchangeForecast.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public class AverageForecastServiceTest {
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
        // rates.add(new Rate(LocalDate.now(), 12, USD)) - nextDay;
        // rates.add(new Rate(LocalDate.now().plusDays(1), 12.43, USD)) - tomorrow;
        return rates;
    }

    @Test()
    void shouldForecastNextDay() {
        Rate forecastRate = service.forecastNextDay(mockRates());
        assertThat(forecastRate).isNotNull();
        assertThat(forecastRate.getExchangeRate()).isEqualTo(BigDecimal.valueOf(1200, 2));
        assertThat(forecastRate.getDate()).isToday();
        assertThat(forecastRate.getCurrency()).isEqualTo(USD);
    }

    @Test
    void shouldForecastNextWeek() {
        List<Rate> forecastRates = service.forecastNextWeek(mockRates());
        Rate rateFromCollection = forecastRates.get(0);
        Rate rateFromService = service.forecastTomorrow(mockRates());
        assertThat(forecastRates).isNotNull();
        assertThat(rateFromService.getCurrency()).isEqualTo(rateFromCollection.getCurrency());
        assertThat(rateFromService.getExchangeRate()).isEqualTo(rateFromCollection.getExchangeRate());
        assertThat(rateFromService.getDate()).isEqualTo(rateFromCollection.getDate());
    }

    @Test
    void shouldForecastTomorrow() {
        Rate forecastRate = service.forecastTomorrow(mockRates());
        assertThat(forecastRate).isNotNull();
        assertThat(forecastRate.getExchangeRate()).isEqualTo(BigDecimal.valueOf(1243, 2));
        assertThat(forecastRate.getDate()).isEqualTo(LocalDate.now().plusDays(1));
        assertThat(forecastRate.getCurrency()).isEqualTo(USD);
    }
}