package com.exchangeForecast.domain;

import com.exchangeForecast.exceptions.NotValidException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ForecastPeriod {

    TOMORROW(1, "tomorrow"),
    WEEK(7, "week"),
    MONTH(30, "month");

    private final int daysCount;
    private final String name;

    public static ForecastPeriod ofName(String name) {
        return Arrays.stream(values())
                .filter(period -> period.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotValidException(name + " forecast period is not valid!"));
    }
}
