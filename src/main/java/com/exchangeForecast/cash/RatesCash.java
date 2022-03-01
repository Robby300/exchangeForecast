package com.exchangeForecast.cash;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.NotValidException;
import com.exchangeForecast.link.ExchangeRateLinks;
import com.exchangeForecast.parser.RatesParser;

import java.io.File;
import java.util.List;

public class RatesCash {
    private final ExchangeRateLinks links = new ExchangeRateLinks();
    private final RatesParser ratesParser = new RatesParser();

    private static List<Rate> eurRates;
    private static List<Rate> usdRates;
    private static List<Rate> tryRates;

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

    public List<Rate> getEurRates() {
        eurRates = getRatesByLinkAndHash(eurRates, eurFileHash, links.getEuroLink());
        return eurRates;
    }

    public List<Rate> getUsdRates() {
        usdRates = getRatesByLinkAndHash(usdRates, usdFileHash, links.getDollarLink());
        return usdRates;
    }

    public List<Rate> getTryRates() {
        tryRates = getRatesByLinkAndHash(tryRates, tryFileHash, links.getLiraLink());
        return tryRates;
    }

    private List<Rate> getRatesByLinkAndHash(List currentRates, int fileHash, String link) {
        if (currentRates != null && fileHash == getHash(link)) {
            return currentRates;
        }
        fileHash = getHash(link);
        currentRates = ratesParser.getRatesFromFile(link);
        return currentRates;
    }

    public int getHash(String link) {
        File file = new File(link);
        return file.hashCode();
    }
}
