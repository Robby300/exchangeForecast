package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.domain.Rate;
import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphOutputService implements OutputService {
    @Override
    public void output(Update update, SendBotMessageService sendBotMessageService, List<Rate> rates) {
        List<Double> x = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            x.add(((double) i) / 10);
        }
        List<Double> C = rates.stream().map(rate -> rate.getExchangeRate().doubleValue()).collect(Collectors.toList());


        Plot plt = Plot.create();
        plt.plot().add(x, C);


        plt.title("histogram");
        plt.savefig("/tmp/histogram.png").dpi(200);
// Don't miss this line to output the file!
        try {
            plt.executeSilently();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PythonExecutionException e) {
            e.printStackTrace();
        }
        sendBotMessageService.sendPhoto(update.getMessage().getChatId().toString(), "histogram.png");
    }
}
