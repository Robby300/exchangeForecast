package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

public interface OutputService {
    void output(Update update, SendBotMessageService sendBotMessageService, List<Rate> rates) throws PythonExecutionException, IOException;
}
