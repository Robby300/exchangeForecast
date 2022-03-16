package com.exchangeForecast.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RateCommandPartsParserTest {
    @Mock
    Update update;

    @InjectMocks
    RateCommandPartsParser partsParser;

    private final String commandLine = "rate USD,EUR,TRY -period week -alg moon -output graph";
    @Test
    public void testExtractRateCommandParts() {
        /*Message message = mock(Message.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn(commandLine);
        partsParser.getRateCommandParts();

        assertThat(partsParser.getCdx()).isNotNull();
        assertThat(partsParser.getDate()).isNull();*/

        //assertThat(partsParser.getCdx()).isEqualTo(List.of(Currency.USD, Currency.EUR, Currency.TRY));
    }
}