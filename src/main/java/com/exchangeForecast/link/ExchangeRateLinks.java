package com.exchangeForecast.link;

import com.exchangeForecast.domain.Currency;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

@Getter
public class ExchangeRateLinks {
    private final ImmutableMap<Currency, String> LinksByCurrency;

    public ExchangeRateLinks() {
        LinksByCurrency = ImmutableMap.<Currency, String>builder()
                .put(Currency.EUR, "files/EUR_F01_02_2005_T05_03_2022.csv")
                .put(Currency.USD, "files/USD_F01_02_2005_T05_03_2022.csv")
                .put(Currency.TRY, "files/TRY_F01_02_2005_T05_03_2022.csv")
                .put(Currency.AMD, "files/AMD_F01_02_2005_T05_03_2022.csv")
                .put(Currency.BGN, "files/BGN_F01_02_2005_T05_03_2022.csv")
                .build();
    }
}
