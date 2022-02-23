package com.exchangeForecast.parsers;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RatesParser {

    private static final String EURO_RATE_FILE = "src/main/resources/static/EUR_F01_02_2002_T01_02_2022.csv";
    private static final String LIRA_RATE_FILE = "src/main/resources/static/TRY_F01_02_2002_T01_02_2022.csv";
    private static final String USDOLLAR_RATE_FILE = "src/main/resources/static/USD_F01_02_2002_T01_02_2022.csv";

    private Rate parseRateRow(String rateRow) {
        String[] rateParts = rateRow.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Rate rate = new Rate(LocalDate.parse(rateParts[0], formatter),
                Double.parseDouble(rateParts[1].replace(",", ".")),
                Currency.of(rateParts[2]));
        return rate;
    }

    private List<Rate> getRatesFromFile(String filePath) {
        List<Rate> rates = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            List<String> rateRows = bufferedReader.lines().skip(1).collect(Collectors.toList());
            Collections.reverse(rateRows);
            rates = rateRows.stream().map(this::parseRateRow).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("CSV файл недоступен.");
        }
        return rates;
    }

    public List<Rate> getRatesByCDX(String currency) {
        List<Rate> rates = null;
        switch (currency) {
            case "EUR":
                rates = getRatesFromFile(EURO_RATE_FILE);
                break;
            case "TRY":
                rates = getRatesFromFile(LIRA_RATE_FILE);
                break;
            case "USD":
                rates = getRatesFromFile(USDOLLAR_RATE_FILE);
        }
        return rates;
    }
}
