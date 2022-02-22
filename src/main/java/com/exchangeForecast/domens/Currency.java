package com.exchangeForecast.domens;

public enum Currency {
    EUR("Евро"),
    TRY("Лира"),
    USD("Доллар");

    private String name;

    public String getName() {
        return name;
    }

    Currency(String name) {
        this.name = name;
    }
}
