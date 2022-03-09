package com.exchangeForecast.service;

import com.exchangeForecast.domain.Rate;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class GraphOutputService implements OutputService {
    @Override
    public void output(Update update, SendBotMessageService sendBotMessageService, List<Rate> rates){
    }
}
