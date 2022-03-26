package com.exchangeForecast.parser;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class RatesParserTest {

    @Test
    public void shouldParseRateRow() {
        RatesParser ratesParser = new RatesParser();
        String rateRow = "10;26.02.2022;\"59,400\";Турецкая лира";
        Rate rate = ratesParser.parseRateRow(rateRow);

        assertThat(rate.getExchangeRate())
                .isEqualTo(BigDecimal.valueOf(5.9400));
        assertThat(rate.getCurrency()).isEqualTo(Currency.TRY);
    }
}