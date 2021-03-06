package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphOutputMethod implements OutputMethod {
    private static final Logger logger = LoggerFactory.getLogger(GraphOutputMethod.class);
    @Override
    public void output(SendMessageService sendMessageService, List<List<Rate>> listOfRates) {
        StringBuilder tittle = new StringBuilder("Exchange forecast for ");
        List<Double> x = new ArrayList<>();
        for (int i = 1; i < listOfRates.get(0).size() + 1; i++) {
            x.add((double) i);
        }
        Plot plot = Plot.create();
        for (List<Rate> rates : listOfRates) {
            List<Double> y = rates.stream().map(rate -> rate.getExchangeRate().doubleValue()).collect(Collectors.toList());
            plot.plot().add(x, y);
            tittle.append(rates.get(0).getCurrency().toString()).append(", ");
        }
        plot.title(tittle.substring(0, tittle.length() - 2) + ".");
        plot.xlabel("Days:");
        plot.ylabel("Rate:");
        plot.savefig("/tmp/histogram.png").dpi(200);

        try {
            plot.executeSilently();
        } catch (IOException | PythonExecutionException e) {
            logger.warn("Plot maker error", e);
        }
        sendMessageService.sendPhoto();
    }
}
