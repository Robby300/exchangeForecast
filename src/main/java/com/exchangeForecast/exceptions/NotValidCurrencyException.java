package com.exchangeForecast.exceptions;

public class NotValidCurrencyException extends RuntimeException {
    public NotValidCurrencyException(String message) {
        super(message);
    }
}
