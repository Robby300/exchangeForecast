package com.exchangeForecast.cash;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.link.ExchangeRateLinks;
import com.exchangeForecast.parser.RatesParser;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class RatesInMemory {
    private static final Logger logger = LoggerFactory.getLogger(RatesInMemory.class);
    private final ExchangeRateLinks links = new ExchangeRateLinks();
    private final ImmutableMap<Currency, String> linksByCurrency = links.getLinksByCurrency();
    private final RatesParser ratesParser = new RatesParser();
    private final Map<Currency, List<Rate>> ratesByCurrency = ImmutableMap.<Currency, List<Rate>>builder()
            .put(Currency.EUR, ratesParser.getRatesFromFile(linksByCurrency.get(Currency.EUR)))
            .put(Currency.USD, ratesParser.getRatesFromFile(linksByCurrency.get(Currency.USD)))
            .put(Currency.TRY, ratesParser.getRatesFromFile(linksByCurrency.get(Currency.TRY)))
            .put(Currency.AMD, ratesParser.getRatesFromFile(linksByCurrency.get(Currency.AMD)))
            .put(Currency.BGN, ratesParser.getRatesFromFile(linksByCurrency.get(Currency.BGN)))
            .build();

    public List<Rate> getRatesByCDX(Currency currency) {
        try {
            return ratesByCurrency.get(currency);
        } catch (Exception e) {
            logger.error("CDX not found", e);
            throw new NotValidException("CDX not found");
        }
    }
}
