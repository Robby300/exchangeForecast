package com.exchangeForecast.service.algorithmService;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;

import java.time.LocalDate;
import java.util.List;

public interface ForecastService {
    List<Rate> forecast(RatesCash cash, Currency cdx, ForecastPeriod period, LocalDate date);
}
