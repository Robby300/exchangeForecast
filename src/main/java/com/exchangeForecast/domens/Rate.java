package com.exchangeForecast.domens;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Rate {
    private LocalDate date;
    private double exchangeRate;
    private Currency currency;

    public Rate(LocalDate date, double exchangeRate, Currency currency) {
        this.date = date;
        this.exchangeRate = exchangeRate;
        this.currency = currency;
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
        return "Rate{" +
                "date=" + date.format(DateTimeFormatter.ISO_DATE) +
                "date= " + date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("Rus"))+
                ", exchangeRate=" + exchangeRate +
                ", currency=" + currency.getName() +
                '}';
    }
}
