package com.exchangeForecast.parser;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateCommandPartsParserTest {
    @Mock
    private final Update update = new Update();

    @InjectMocks
    RateCommandPartsParser partsParser = new RateCommandPartsParser(update);

    private final String commandLine = "rate USD,EUR,TRY -period week -alg moon -output graph";
    @Test
    public void testExtractRateCommandParts() {
        when(update.getMessage().getText()).thenReturn(commandLine);

        partsParser.extractRateCommandParts();

        assertThat(partsParser.getCdx()).isNotNull();
        //assertThat(partsParser.getDate()).isNull();

        //assertThat(partsParser.getCdx()).isEqualTo(List.of(Currency.USD, Currency.EUR, Currency.TRY));
    }
}