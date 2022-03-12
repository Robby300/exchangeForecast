package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MoonForecastServiceTest {


    ForecastService service = new MoonForecastService();
    List<Rate> mockRates = List.of(
            new Rate(LocalDate.of(2021, 12, 19), BigDecimal.valueOf(30), Currency.USD),
            new Rate(LocalDate.of(2022, 1, 18), BigDecimal.valueOf(10), Currency.USD),
            new Rate(LocalDate.of(2022, 2, 16), BigDecimal.valueOf(20), Currency.USD)
            );

    @Test
    public void shouldForecastByPeriod() {
        List<Rate> forecastRates = service.forecastByPeriod(mockRates, ForecastPeriod.WEEK);

        assertThat(forecastRates).isNotNull();
        assertThat(forecastRates.get(1).getExchangeRate())
                .isCloseTo(forecastRates.get(0).getExchangeRate(),
                        within(forecastRates.get(0).getExchangeRate().multiply(BigDecimal.valueOf(0.1))));
    }

    @Test
    public void shouldTestForecastByDate() {
        Rate rate = service.forecastByDate(mockRates, LocalDate.now().plusDays(1));

        assertThat(rate).isNotNull();
        assertThat(rate.getCurrency()).isEqualTo(Currency.USD);
        assertThat(rate.getExchangeRate()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(rate.getDate().minusDays(1)).isToday();
    }
}