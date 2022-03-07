package com.exchangeForecast.service;

import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LinearRegressionForecastService implements ForecastService {
    private static final int SAMPLE_SIZE = 7;
    private double xxbar = 0.0;
    private double yybar = 0.0;
    private double xybar = 0.0;

    public Rate forecastNextDay(List<Rate> rates) {
        int sampleSize = rates.size();
        int[] days;
        double[] exchangeRates;
        // first pass: read in data, compute xbar and ybar
        double sumDays, sumExchangeRates, sumSquareDays;
        days = IntStream.range(0, rates.size()).toArray();
        exchangeRates = rates.stream().mapToDouble(rate -> rate.getExchangeRate().doubleValue()).toArray();
        sumDays = Arrays.stream(days).sum();
        sumSquareDays = Arrays.stream(days).map(day -> day * day).sum();
        sumExchangeRates = Arrays.stream(exchangeRates).sum();

        double xbar = sumDays / sampleSize;
        double ybar = sumExchangeRates / sampleSize;

        // second pass: compute summary statistics
        computeSummaryStatistics(sampleSize, days, exchangeRates, xbar, ybar);
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
        System.out.println("model of linear regression   = " + beta1 + " * days + " + beta0);

        // analyze results
        int df = sampleSize - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < sampleSize; i++) {
            double fit = beta1 * days[i] + beta0;
            rss += (fit - exchangeRates[i]) * (fit - exchangeRates[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2 = ssr / yybar;
        double svar = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar / sampleSize + xbar * xbar * svar1;
        System.out.println("R^2                 = " + R2);
        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
        svar0 = svar * sumSquareDays / (sampleSize * xxbar);
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

        System.out.println("SSTO = " + yybar);
        System.out.println("SSE  = " + rss);
        System.out.println("SSR  = " + ssr);
        System.out.println(rates.size());
        //System.out.println(beta1 * rates.size() + beta0);
        BigDecimal forecastExchangeRate = BigDecimal.valueOf(beta1 * rates.size() + beta0);
        return new Rate.Builder()
                .date(rates.get(rates.size() - 1).getDate().plusDays(1))
                .exchangeRate(forecastExchangeRate)
                .currency(rates.get(0).getCurrency())
                .build();
    }

    private void computeSummaryStatistics(int dataSize, int[] days, double[] exchangeRates, double xbar, double ybar) {
        for (int i = 0; i < dataSize; i++) {
            xxbar += (days[i] - xbar) * (days[i] - xbar);
            yybar += (exchangeRates[i] - ybar) * (exchangeRates[i] - ybar);
            xybar += (days[i] - xbar) * (exchangeRates[i] - ybar);
        }
    }

    private List<Rate> getLastMonthSubList(List<Rate> rates) {
        return rates.subList(getIndexOfMonthBeforeRate(rates), rates.size());
    }

    private int getIndexOfMonthBeforeRate(List<Rate> rates) {
        Rate lastRate = rates.get(rates.size() - 1);
        LocalDate lastDate = lastRate.getDate();
        LocalDate monthBeforeDate = lastDate.minusMonths(1);
        Rate rateMonthBefore = rates.stream().filter(rate -> rate.getDate().isAfter(monthBeforeDate)).findFirst().get();
        return rates.indexOf(rateMonthBefore);
    }

    @Override
    public List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
        while (!getLastRate(rates).getDate().equals(LocalDate.now().plusDays(period.getDaysCount()))) {
            rates.add(forecastNextDay(getLastMonthSubList(rates)));
        }
        return rates.subList(rates.size() - period.getDaysCount(), rates.size() - 1);
    }

    @Override
    public Rate forecastByDate(List<Rate> rates, LocalDate date) {
        while (getLastRate(rates).getDate().isAfter(date.plusDays(1))) {
            rates.add(forecastNextDay(getLastMonthSubList(rates)));
        }
        return rates.stream().filter(rate -> rate.getDate().equals(date)).findFirst().get();
    }

    private Rate getLastRate(List<Rate> rates) {
        return rates.get(rates.size() - 1);
    }
}
