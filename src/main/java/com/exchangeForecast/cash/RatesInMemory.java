package com.exchangeForecast.cash;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.link.ExchangeRateLinks;
import com.exchangeForecast.parser.RatesParser;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.util.List;
import java.util.Map;

public class RatesInMemory {
    private final ExchangeRateLinks links = new ExchangeRateLinks();
    private final RatesParser ratesParser = new RatesParser();
    private final Map<Currency, List<Rate>> ratesByCurrency = ImmutableMap.<Currency, List<Rate>>builder()
            .put(Currency.EUR, ratesParser.getRatesFromFile(links.getEurLink()))
            .put(Currency.USD, ratesParser.getRatesFromFile(links.getUsdLink()))
            .put(Currency.TRY, ratesParser.getRatesFromFile(links.getTryLink()))
            .put(Currency.AMD, ratesParser.getRatesFromFile(links.getAmdLink()))
            .put(Currency.BGN, ratesParser.getRatesFromFile(links.getBgnLink()))
            .build();

    public List<Rate> getRatesByCDX(Currency currency) {
        try {
            return ratesByCurrency.get(currency);
        } catch (Exception e) {
            throw new NotValidException("CDX not found");
        }
    }
}
