package com.exchangeForecast.service;

import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;

import java.time.LocalDate;
import java.util.List;

public interface ForecastService {
    List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period);
    Rate forecastByDate(List<Rate> rates, LocalDate date);
}
