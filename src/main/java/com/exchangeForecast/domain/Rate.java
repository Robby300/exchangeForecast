package com.exchangeForecast.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Rate {
    private LocalDate date;
    private double exchangeRate;
    private Currency currency;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Rate(LocalDate date, double exchangeRate, Currency currency) {
        this.date = date;
        this.exchangeRate = exchangeRate;
        this.currency = currency;
    }

    public Rate() {
    }

    public Rate(Builder builder) {
        this.date = builder.date;
        this.exchangeRate = Math.round(builder.exchangeRate * 100) / 100D;
        this.currency = builder.currency;
    }

    public static class Builder {
        private LocalDate date;
        private double exchangeRate;
        private Currency currency;

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder exchangeRate(double exchangeRate) {
            this.exchangeRate = exchangeRate;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Rate build() {
            return new Rate(this);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    @Override
    public String toString() {
        return date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ROOT) + " " +
                date.format(formatter) + " " + exchangeRate + " " + currency.getName();
    }
}
