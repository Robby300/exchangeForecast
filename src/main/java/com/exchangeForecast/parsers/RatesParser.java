package com.exchangeForecast.parsers;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RatesParser {

    private static final String EURO_RATE_FILE = "src/main/resources/static/EUR_F01_02_2002_T01_02_2022.csv";
    private static final String LIRAPATHLINE = "src/main/resources/static/TRY_F01_02_2002_T01_02_2022.csv";
    private static final String DOLLARPATHLINE = "src/main/resources/static/USD_F01_02_2002_T01_02_2022.csv";

    private static Rate parseRateRow(String rateRow) {
        String[] rateParts = rateRow.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Rate rate = new Rate(LocalDate.parse(rateParts[0], formatter),
                Double.parseDouble(rateParts[1].replace(",", ".")),
                Currency.of(rateParts[2]));
        return rate;
    }

    public static List<Rate> getRates(String filePath) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        List<String> rateRows = bufferedReader.lines().skip(1).collect(Collectors.toList());
        Collections.reverse(rateRows);
        List<Rate> rates = rateRows.stream().map(RatesParser::parseRateRow).collect(Collectors.toList());
        return rates;
    }
}
