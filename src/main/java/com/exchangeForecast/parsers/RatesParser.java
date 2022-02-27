package com.exchangeForecast.parsers;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RatesParser {

    private Rate parseRateRow(String rateRow) {
        String[] rateParts = rateRow.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return new Rate.Builder()
                .date(LocalDate.parse(rateParts[0], formatter))
                .exchangeRate(BigDecimal.valueOf(Double.parseDouble(rateParts[1].replace(",", "."))))
                .currency(Currency.ofDbName(rateParts[2]))
                .build();
    }

    public List<Rate> getRatesFromFile(String filePath) {
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
}
