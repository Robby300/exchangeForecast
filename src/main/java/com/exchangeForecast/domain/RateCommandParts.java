package com.exchangeForecast.domain;

import com.exchangeForecast.service.forecastService.ForecastService;
import com.exchangeForecast.service.outputServcie.OutputMethod;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RateCommandParts {
    private List<Currency> cdx;
    private ForecastPeriod period;
    private LocalDate date;
    private ForecastService algorithm;
    private OutputMethod outputMethod;
}
