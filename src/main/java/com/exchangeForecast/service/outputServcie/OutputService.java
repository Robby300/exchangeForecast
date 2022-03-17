package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;

import java.util.List;

public interface OutputService {
    void output(SendBotMessageService sendBotMessageService, List<List<Rate>> listOfRates);
}
