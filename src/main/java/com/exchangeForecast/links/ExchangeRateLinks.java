package com.exchangeForecast.links;

public class ExchangeRateLinks {
    String EURO_RATE_FILE = "src/main/resources/static/EUR_F01_02_2002_T01_02_2022.csv";
    String LIRA_RATE_FILE = "src/main/resources/static/TRY_F01_02_2002_T01_02_2022.csv";
    String DOLLAR_RATE_FILE = "src/main/resources/static/USD_F01_02_2002_T01_02_2022.csv";

    public String getEuroLink() {
        return EURO_RATE_FILE;
    }

    public String getLiraLink() {
        return LIRA_RATE_FILE;
    }

    public String getDollarLink() {
        return DOLLAR_RATE_FILE;
    }
}
