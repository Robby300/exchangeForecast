package com.exchangeForecast.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@AllArgsConstructor
@Builder
@Getter
public class Rate {
    private final LocalDate date;
    private final BigDecimal exchangeRate;
    private final Currency currency;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public String toString() {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ROOT) + " " +
                date.format(formatter) + " - " + exchangeRate.setScale(2, RoundingMode.HALF_DOWN);
    }
}
