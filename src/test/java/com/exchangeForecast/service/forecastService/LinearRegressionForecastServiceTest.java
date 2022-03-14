package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;
public class LinearRegressionForecastServiceTest {

    final List<Rate> mockRates = List.of(
            new Rate(LocalDate.now().minusDays(3), BigDecimal.valueOf(20), Currency.USD),
            new Rate(LocalDate.now().minusDays(2), BigDecimal.valueOf(30), Currency.USD),
            new Rate(LocalDate.now().minusDays(1), BigDecimal.valueOf(40), Currency.USD)
    );

    @Test
    public void shouldForecastByDate() {
        ForecastService service = new LinearRegressionForecastService();

        Rate forecastByDate = service.forecastByDate(mockRates, LocalDate.now());
        assertThat(forecastByDate).isNotNull();
        assertThat(forecastByDate.getExchangeRate()).isEqualTo(BigDecimal.valueOf(50d));
    }
}