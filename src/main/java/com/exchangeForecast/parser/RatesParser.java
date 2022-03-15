package com.exchangeForecast.parser;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.NotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RatesParser {
    private static final Logger logger = LoggerFactory.getLogger(RatesParser.class);

    public Rate parseRateRow(String rateRow) {
        String[] rateParts = rateRow.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return Rate.builder()
                .date(LocalDate.parse(rateParts[1], formatter))
                .exchangeRate(new BigDecimal(rateParts[2].substring(1, rateParts[2].length() - 2).replace(",", "."))
                        .divide(BigDecimal.valueOf(Double.parseDouble(rateParts[0])), RoundingMode.HALF_DOWN))
                .currency(Currency.ofDbName(rateParts[3]))
                .build();
    }

    public List<Rate> getRatesFromFile(String filePath) {
        List<Rate> rates;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            List<String> rateRows = bufferedReader.lines().skip(1).collect(Collectors.toList());
            Collections.reverse(rateRows);
            rates = rateRows.stream().map(this::parseRateRow).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("CSV файл недоступен.", e);
            throw new NotValidException("CSV файл недоступен.");
        }
        logger.info("Произведён парсинг {} строк.", rates.size());
        return rates;
    }
}
