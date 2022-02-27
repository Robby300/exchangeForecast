package com.exchangeForecast.exceptions;

public class ForecastNotFound extends RuntimeException {
    public ForecastNotFound(String message) {
        super(message);
    }
}
