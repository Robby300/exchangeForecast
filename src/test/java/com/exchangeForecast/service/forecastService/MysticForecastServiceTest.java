package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MysticForecastServiceTest {


    ForecastService service = new MysticForecastService();
    List<Rate> mockRates = new ArrayList<>(3);
/*
            new Rate(LocalDate.of(2021, 12, 19), BigDecimal.valueOf(30), Currency.USD),
            new Rate(LocalDate.of(2022, 1, 18), BigDecimal.valueOf(10), Currency.USD),
            new Rate(LocalDate.of(2022, 2, 16), BigDecimal.valueOf(20), Currency.USD)
            ).sort(LocalDate::compareTo);
    */

    @Test
    public void shouldForecastByPeriod() {


    }

    @Test
    public void shouldTestForecastByDate() {
        mockRates.add(new Rate(LocalDate.of(2021, 12, 19), BigDecimal.valueOf(30), Currency.USD));
        mockRates.add(new Rate(LocalDate.of(2022, 1, 18), BigDecimal.valueOf(10), Currency.USD));
        mockRates.add(new Rate(LocalDate.of(2022, 2, 16), BigDecimal.valueOf(20), Currency.USD));
        Rate rate = service.forecastByDate(mockRates, LocalDate.now().plusDays(1));

        assertThat(rate).isNotNull();
        assertThat(rate.getCurrency()).isEqualTo(Currency.USD);
        assertThat(rate.getExchangeRate()).isEqualTo(BigDecimal.valueOf(20));
    }
}