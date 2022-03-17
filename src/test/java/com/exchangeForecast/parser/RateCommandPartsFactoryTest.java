package com.exchangeForecast.parser;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.RateCommandParts;
import com.exchangeForecast.service.forecastService.ForecastService;
import com.exchangeForecast.service.forecastService.MoonForecastServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class RateCommandPartsFactoryTest {

    private final String commandLine = "rate USD,EUR,TRY -period week -alg moon -output graph";
    @Test
    public void testExtractRateCommandParts() {
        RateCommandParts rateCommandParts = new RateCommandPartsFactory().getRateCommandParts(commandLine);

        assertThat(rateCommandParts.getCdx()).isNotNull();
        assertThat(rateCommandParts.getDate()).isNull();
        assertThat(rateCommandParts.getPeriod().getDaysCount()).isEqualTo(7);
        assertThat(rateCommandParts.getCdx()).isEqualTo(List.of(Currency.USD, Currency.EUR, Currency.TRY));
    }
}