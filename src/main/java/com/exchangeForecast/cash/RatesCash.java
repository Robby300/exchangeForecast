package com.exchangeForecast.cash;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.links.ExchangeRateLinks;
import com.exchangeForecast.parsers.RatesParser;

import java.io.File;
import java.util.List;

public class RatesCash {
    private final ExchangeRateLinks links = new ExchangeRateLinks();
    private final RatesParser ratesParser = new RatesParser();

    private List<Rate> eurRates;
    private List<Rate> usdRates;
    private List<Rate> tryRates;

    private int eurFileHash = getHash(links.getEuroLink());
    private int usdFileHash = getHash(links.getDollarLink());
    private int tryFileHash = getHash(links.getLiraLink());

    public List<Rate> getRatesByCDX(Currency currency) {
        switch (currency) {
            case EUR:
                return getEurRates();
            case TRY:
                return getTryRates();
            case USD:
                return getUsdRates();
            default:
                throw new NotValidException("CDX not found");
        }
    }

    public int getHash(String link) {
        File file = new File(link);
        return file.hashCode();
    }

    public List<Rate> getEurRates() {
        if (eurRates != null && eurFileHash == getHash(links.getEuroLink())) {
            return usdRates;
        }
        eurFileHash = getHash(links.getEuroLink());
        eurRates = ratesParser.getRatesFromFile(links.getEuroLink());
        return eurRates;
    }

    public List<Rate> getUsdRates() {
        if (usdRates != null && usdFileHash == getHash(links.getDollarLink())) {
            return usdRates;
        }
        usdFileHash = getHash(links.getDollarLink());
        usdRates = ratesParser.getRatesFromFile(links.getDollarLink());
        return usdRates;
    }

    public List<Rate> getTryRates() {
        if (tryRates != null && tryFileHash == getHash(links.getLiraLink())) {
            return tryRates;
        }
        tryFileHash = getHash(links.getLiraLink());
        tryRates = ratesParser.getRatesFromFile(links.getLiraLink());
        return tryRates;
    }
}
