package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class ListOutputService implements OutputService {

    @Override
    public void output(Update update, SendBotMessageService sendBotMessageService, List<Rate> rates) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), ratesToString(rates));
    }

    private String ratesToString(List<Rate> rates) {
        String result = "";
        for (Rate rate :
                rates) {
            result += rate.toString() + "\n";
        }
        return result;
    }
}
