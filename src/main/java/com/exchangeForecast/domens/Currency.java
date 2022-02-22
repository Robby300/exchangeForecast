package com.exchangeForecast.domens;

import com.exchangeForecast.exceptions.NotValidCurrencyException;

import java.util.Arrays;

public enum Currency {
    EUR("Евро"),
    TRY("Лира"),
    USD("Доллар");

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
                .orElseThrow(() -> new NotValidCurrencyException(name + " is not valid!"));
    }
}
