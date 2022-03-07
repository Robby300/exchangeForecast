package com.exchangeForecast.domain;

import com.exchangeForecast.exceptions.NotValidException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Currency {
    EUR("Евро", "EUR"),
    TRY("Турецкая лира", "TRY"),
    USD("Доллар США", "USD"),
    AMD("Армянский драм", "AMD"),
    BGN("Болгарский лев", "BGN");

    private final String dbName;
    private final String consoleName;

    public static Currency ofConsoleName(String consoleName) {
        return Arrays.stream(values())
                .filter(currency -> currency.getConsoleName().equals(consoleName))
                .findFirst()
                .orElseThrow(() -> new NotValidException(consoleName + " CDX is not valid!"));
    }

    public static Currency ofDbName(String dbName) {
        return Arrays.stream(values())
                .filter(currency -> currency.getDbName().equals(dbName))
                .findFirst()
                .orElseThrow(() -> new NotValidException(dbName + " CDX is not valid!"));
    }
}
