package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;

import java.util.List;

public class ListOutputService implements OutputService {

    @Override
    public void output(SendBotMessageService sendBotMessageService, List<List<Rate>> listOfRates) {
        for (List<Rate> rates : listOfRates) {
            sendBotMessageService.sendMessage(ratesToString(rates));
        }
    }

    private String ratesToString(List<Rate> rates) {
        StringBuilder result = new StringBuilder();
        for (Rate rate :
                rates) {
            result.append(rate.toString()).append("\n");
        }
        return result.toString();
    }
}
