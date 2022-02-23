package com.exchangeForecast.domain;

import com.exchangeForecast.exceptions.NotValidException;

import java.util.Arrays;

public enum Currency {
    EUR("Евро"),
    TRY("Турецкая лира"),
    USD("Доллар США");

    private final String name;

    public String getName() {
        return name;
    }

    Currency(String name) {
        this.name = name;
    }

    public static Currency of(String name) {
        return Arrays.stream(values())
                .filter(currency -> currency.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotValidException(name + " is not valid!"));
    }
}
