package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ActualForecastServiceTest {
    private final ForecastService service = new ActualForecastService();
    private final List<Rate> mockRates = List.of(
            new Rate(LocalDate.now().minusYears(2), BigDecimal.valueOf(30), Currency.USD),
            new Rate(LocalDate.now().minusYears(3), BigDecimal.valueOf(10), Currency.USD)
    );

    @Test
    public void testForecastByDate() {
        Rate forecastByDate = service.forecastByDate(mockRates, LocalDate.now());

        assertThat(forecastByDate).isNotNull();
        assertThat(forecastByDate.getDate()).isEqualTo(LocalDate.now());
        assertThat(forecastByDate.getExchangeRate()).isEqualTo(BigDecimal.valueOf(40));
    }
}