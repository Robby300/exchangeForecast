package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;
import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphOutputService implements OutputService {
    @Override
    public void output(Update update, SendBotMessageService sendBotMessageService, List<Rate> rates) {
        List<Double> x = new ArrayList<>();
        for (int i = 1; i < rates.size() + 1; i++) {
            x.add((double) i);
        }

        List<Double> y = rates.stream().map(rate -> rate.getExchangeRate().doubleValue()).collect(Collectors.toList());


        Plot plt = Plot.create();

        plt.plot().add(x, y);
        plt.xlabel("Days:");
        plt.ylabel("Rate:");
        plt.title("Exchange forecast for " + rates.get(0).getCurrency());
        plt.savefig("/tmp/histogram.png").dpi(200);
        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
        sendBotMessageService.sendPhoto(update.getMessage().getChatId().toString(), "histogram.png");
    }
}
