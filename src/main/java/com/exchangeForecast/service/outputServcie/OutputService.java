package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface OutputService {
    void output(Update update, SendBotMessageService sendBotMessageService, List<List<Rate>> listOfRates);
}
