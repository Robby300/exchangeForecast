package com.exchangeForecast.service;

import com.exchangeForecast.domain.Rate;

import java.util.List;

public interface ForecastService {
    Rate forecastTomorrow(List<Rate> rates);
    List<Rate> forecastNextWeek(List<Rate> rates);
}
