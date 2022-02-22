package com.exchangeForecast;

import com.exchangeForecast.domens.Currency;
import com.exchangeForecast.domens.Rate;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;
import com.exchangeForecast.service.LinearRegressionForecastService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
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

    private static List<Rate> getRates(String filePath) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        List<String> rateRows = bufferedReader.lines().skip(1).collect(Collectors.toList());
        Collections.reverse(rateRows);
        List<Rate> rates = rateRows.stream().map(Main::parseRateRow).collect(Collectors.toList());
        return rates;
    }


    public static void main(String[] args) throws FileNotFoundException {

        //LinearRegressionForecastService linearRegressionForecastService = new LinearRegressionForecastService();
        //linearRegressionForecastService.forecastTomorrow(getRates(EURO_RATE_FILE));

        ForecastService forecastService = new AverageForecastService();
        forecastService.forecastTomorrow(getRates(EURO_RATE_FILE));
    }
}
