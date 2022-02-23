package com.exchangeForecast.service;

import com.exchangeForecast.domain.Rate;

import java.util.List;

public interface ForecastService {
    Rate forecastNextDay(List<Rate> rates);
    List<Rate> forecastNextWeek(List<Rate> rates);
}
