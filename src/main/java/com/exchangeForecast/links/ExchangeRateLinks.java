package com.exchangeForecast.links;

public class ExchangeRateLinks {
    private final String EURO_RATE_FILE = "src/main/resources/static/EUR_F01_02_2002_T01_02_2022.csv";
    private final String LIRA_RATE_FILE = "src/main/resources/static/TRY_F01_02_2002_T01_02_2022.csv";
    private final String DOLLAR_RATE_FILE = "src/main/resources/static/USD_F01_02_2002_T01_02_2022.csv";

    public String getEURO_RATE_FILE() {
        return EURO_RATE_FILE;
    }

    public String getLIRA_RATE_FILE() {
        return LIRA_RATE_FILE;
    }

    public String getDOLLAR_RATE_FILE() {
        return DOLLAR_RATE_FILE;
    }
}
