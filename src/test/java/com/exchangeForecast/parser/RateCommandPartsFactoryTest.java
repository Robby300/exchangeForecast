package com.exchangeForecast.parser;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.RateCommandParts;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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