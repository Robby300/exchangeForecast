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

    private List<Rate> eurRates;
    private List<Rate> usdRates;
    private List<Rate> tryRates;
    private List<Rate> bgnRates;
    private List<Rate> amdRates;

    private int eurFileHash = getHash(links.getEurLink());
    private int usdFileHash = getHash(links.getUsdLink());
    private int tryFileHash = getHash(links.getTryLink());
    private int amdFileHash = getHash(links.getAmdLink());
    private int bgnFileHash = getHash(links.getBgnLink());

    public List<Rate> getRatesByCDX(Currency currency) {
        switch (currency) {
            case EUR:
                return getEurRates();
            case TRY:
                return getTryRates();
            case USD:
                return getUsdRates();
            case AMD:
                return getAmdRates();
            case BGN:
                return getBgnRates();
            default:
                throw new NotValidException("CDX not found");
        }
    }

    public List<Rate> getEurRates() {
        eurRates = getRatesByLinkAndHash(eurRates, eurFileHash, links.getEurLink());
        eurFileHash = getHash(links.getEurLink());
        return eurRates;
    }

    public List<Rate> getUsdRates() {
        usdRates = getRatesByLinkAndHash(usdRates, usdFileHash, links.getUsdLink());
        usdFileHash = getHash(links.getUsdLink());
        return usdRates;
    }

    public List<Rate> getTryRates() {
        tryRates = getRatesByLinkAndHash(tryRates, tryFileHash, links.getTryLink());
        tryFileHash = getHash(links.getTryLink());
        return tryRates;
    }

    public List<Rate> getAmdRates() {
        amdRates = getRatesByLinkAndHash(amdRates, amdFileHash, links.getAmdLink());
        amdFileHash = getHash(links.getAmdLink());
        return amdRates;
    }

    public List<Rate> getBgnRates() {
        bgnRates = getRatesByLinkAndHash(bgnRates, bgnFileHash, links.getBgnLink());
        bgnFileHash = getHash(links.getBgnLink());
        return bgnRates;
    }

    private List<Rate> getRatesByLinkAndHash(List<Rate> currentRates, int fileHash, String link) {
        if (currentRates != null && fileHash == getHash(link)) {
            return currentRates;
        }
        return ratesParser.getRatesFromFile(link);
    }

    public int getHash(String link) {
        File file = new File(link);
        return file.hashCode();
    }
}
