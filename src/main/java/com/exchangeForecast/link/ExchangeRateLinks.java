package com.exchangeForecast.link;

public class ExchangeRateLinks {
    static final String AMD_RATE_FILE = "files/AMD_F01_02_2005_T05_03_2022.csv";
    static final String BGN_RATE_FILE = "files/BGN_F01_02_2005_T05_03_2022.csv";
    static final String EUR_RATE_FILE = "files/EUR_F01_02_2005_T05_03_2022.csv";
    static final String TRY_RATE_FILE = "files/TRY_F01_02_2005_T05_03_2022.csv";
    static final String USD_RATE_FILE = "files/USD_F01_02_2005_T05_03_2022.csv";

    public String getAmdLink() {
        return AMD_RATE_FILE;
    }

    public String getBgnLink() {
        return BGN_RATE_FILE;
    }

    public String getEurLink() {
        return EUR_RATE_FILE;
    }

    public String getTryLink() {
        return TRY_RATE_FILE;
    }

    public String getUsdLink() {
        return USD_RATE_FILE;
    }


}
