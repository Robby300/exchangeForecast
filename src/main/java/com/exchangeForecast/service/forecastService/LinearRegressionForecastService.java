package com.exchangeForecast.service.forecastService;

import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.IndexNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LinearRegressionForecastService extends ForecastService {
    private static final Logger logger = LoggerFactory.getLogger(LinearRegressionForecastService.class);

    private double xxbar = 0.0;
    private double xybar = 0.0;

    private void computeSummaryStatistics(int dataSize, int[] days,
                                          double[] exchangeRates, double xbar, double ybar) {
        for (int i = 0; i < dataSize; i++) {
            xxbar += (days[i] - xbar) * (days[i] - xbar);
            xybar += (days[i] - xbar) * (exchangeRates[i] - ybar);
        }
    }

    @AllArgsConstructor
    private class RegressionModel {
        private final BigDecimal betta1;
        private final BigDecimal betta0;
    }

    private RegressionModel getRegressionModel(List<Rate> rates) {
        int sampleSize = rates.size();
        int[] days;
        double[] exchangeRates;
        double sumDays, sumExchangeRates;
        days = IntStream.range(0, rates.size()).toArray();
        exchangeRates = rates.stream().mapToDouble(rate -> rate.getExchangeRate().doubleValue()).toArray();
        sumDays = Arrays.stream(days).sum();
        sumExchangeRates = Arrays.stream(exchangeRates).sum();
        double xbar = sumDays / sampleSize;
        double ybar = sumExchangeRates / sampleSize;
        computeSummaryStatistics(sampleSize, days, exchangeRates, xbar, ybar);
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;
        logger.info("model of linear regression   = {} * days + {}", beta1, beta0);
        return new RegressionModel(BigDecimal.valueOf(beta1), BigDecimal.valueOf(beta0));
    }

    private int getIndexOfRateMonthBefore(List<Rate> rates) {
        LocalDate lastDate = getLastRate(rates).getDate();
        LocalDate monthBeforeDate = lastDate.minusMonths(1);
        Rate rateMonthBefore = rates
                .stream()
                .filter(rate -> rate.getDate().isAfter(monthBeforeDate))
                .findFirst()
                .orElseThrow(() -> new IndexNotFoundException("index not founded"));
        return rates.indexOf(rateMonthBefore);
    }

    @Override
    public List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
        List<Rate> resultRates = new ArrayList<>();
        RegressionModel model = getRegressionModel(getLastMonthSubList(rates));
        List<Rate> lastMonthSubList = getLastMonthSubList(rates);
        for (int i = 0; i < period.getDaysCount(); i++) {
            resultRates.add(getForecastRateByModel(LocalDate.now().plusDays(i + 1), lastMonthSubList, model));
        }
        return resultRates;
    }

    @Override
    public Rate forecastByDate(List<Rate> rates, LocalDate date) {
        List<Rate> lastMonthRates = getLastMonthSubList(rates);
        RegressionModel model = getRegressionModel(lastMonthRates);
        return getForecastRateByModel(date, lastMonthRates, model);
    }

    private Rate getForecastRateByModel(LocalDate date, List<Rate> lastMonthRates, RegressionModel model) {
        LocalDate lastRateDate = getLastRate(lastMonthRates).getDate();
        BigDecimal daysBetweenFirstRateAndTargetDate = getDaysBetween(date, lastRateDate).add(BigDecimal.valueOf(lastMonthRates.size()));
        BigDecimal forecastExchangeRate = model.betta1.multiply(daysBetweenFirstRateAndTargetDate).add(model.betta0);
        return Rate.builder()
                .date(date)
                .exchangeRate(forecastExchangeRate)
                .currency(getLastRate(lastMonthRates).getCurrency())
                .build();
    }

    private BigDecimal getDaysBetween(LocalDate date, LocalDate lastRateDate) {
        return BigDecimal.valueOf(ChronoUnit.DAYS.between(lastRateDate, date) - 1);
    }

    private List<Rate> getLastMonthSubList(List<Rate> rates) {
        return rates.subList(getIndexOfRateMonthBefore(rates), rates.size());
    }
}
