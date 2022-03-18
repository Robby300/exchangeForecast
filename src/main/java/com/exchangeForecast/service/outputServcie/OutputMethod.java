package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;

import java.util.List;

public interface OutputMethod {
    void output(SendMessageService sendMessageService, List<List<Rate>> listOfRates);
}
