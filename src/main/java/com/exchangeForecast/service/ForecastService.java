package com.exchangeForecast.service;

import com.exchangeForecast.domens.Rate;

import java.util.List;

public interface ForecastService {
    void forecastTomorrow(List<Rate> rates);
}
