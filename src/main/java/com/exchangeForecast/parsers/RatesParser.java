package com.exchangeForecast.parsers;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.links.ExchangeRateLinks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RatesParser {
    ExchangeRateLinks links = new ExchangeRateLinks();

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
        switch (currency) {
            case "EUR":
                return getRatesFromFile(links.getEuroLink());
            case "TRY":
                return getRatesFromFile(links.getLiraLink());
            case "USD":
                return getRatesFromFile(links.getDollarLink());
        }
        return new ArrayList<>();
    }
}
