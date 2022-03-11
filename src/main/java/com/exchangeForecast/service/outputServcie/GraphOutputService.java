package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphOutputService implements OutputService {
    @Override
    public void output(Update update, SendBotMessageService sendBotMessageService, List<List<Rate>> listOfRates) {
        StringBuilder tittle = new StringBuilder("Exchange forecast for ");
        List<Double> x = new ArrayList<>();
        for (int i = 1; i < listOfRates.get(0).size() + 1; i++) {
            x.add((double) i);
        }

        Plot plt = Plot.create();

        for (List<Rate> rates : listOfRates) {
            List<Double> y = rates.stream().map(rate -> rate.getExchangeRate().doubleValue()).collect(Collectors.toList());
            plt.plot().add(x, y);
            tittle.append(rates.get(0).getCurrency().toString()).append(", ");
        }
        plt.title(tittle.toString().trim().substring(0, tittle.length() - 2) + ".");
        plt.xlabel("Days:");
        plt.ylabel("Rate:");
        plt.savefig("/tmp/histogram.png").dpi(200);

        try {
            plt.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            e.printStackTrace();
        }
        sendBotMessageService.sendPhoto(update.getMessage().getChatId().toString(), "histogram.png");
    }
}
