package com.exchangeForecast.service.algorithmService;

import com.exchangeForecast.cash.RatesCash;
import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.ForecastPeriod;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.exceptions.IndexNotFoundException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LinearRegressionForecastService implements ForecastService {
    private double xxbar = 0.0;
    private double yybar = 0.0;
    private double xybar = 0.0;

    @Override
    public List<Rate> forecast(RatesCash cash, Currency cdx, ForecastPeriod period, LocalDate date) {
        List<Rate> rates = cash.getRatesByCDX(cdx);
        if (period != null) {
            return forecastByPeriod(rates, period);
        } else if (date != null) {
            return List.of(forecastByDate(rates, date));
        }
        return rates;
    }

    private void computeSummaryStatistics(int dataSize, int[] days, double[] exchangeRates, double xbar, double ybar) {
        for (int i = 0; i < dataSize; i++) {
            xxbar += (days[i] - xbar) * (days[i] - xbar);
            yybar += (exchangeRates[i] - ybar) * (exchangeRates[i] - ybar);
            xybar += (days[i] - xbar) * (exchangeRates[i] - ybar);
        }
    }

    @AllArgsConstructor
    class RegressionModel {
        private final BigDecimal betta1;
        private final BigDecimal betta0;
    }

    private RegressionModel getRegressionModel(List<Rate> rates) {
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
        //BigDecimal forecastExchangeRate = BigDecimal.valueOf(beta1 * rates.size() + beta0);
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

    private List<Rate> forecastByPeriod(List<Rate> rates, ForecastPeriod period) {
        List<Rate> resultRates = new ArrayList<>();
        for (int i = 0; i < period.getDaysCount(); i++) {
            resultRates.add(forecastByDate(rates, LocalDate.now().plusDays(i + 1)));
        }
        return resultRates;
    }

    private Rate forecastByDate(List<Rate> rates, LocalDate date) {
        List<Rate> lastMonthRates = getLastMonthSubList(rates);
        RegressionModel regressionModel = getRegressionModel(lastMonthRates);
        LocalDate lastRateDate = getLastRate(lastMonthRates).getDate();
        BigDecimal daysBetweenFirstRateAndTargetDate = getDaysBetween(date, lastRateDate).add(BigDecimal.valueOf(lastMonthRates.size()));
        BigDecimal forecastExchangeRate = regressionModel.betta1.multiply(daysBetweenFirstRateAndTargetDate).add(regressionModel.betta0);
        return Rate.builder()
                .date(date)
                .exchangeRate(forecastExchangeRate)
                .currency(getLastRate(lastMonthRates).getCurrency())
                .build();
    }

    private BigDecimal getDaysBetween(LocalDate date, LocalDate lastRateDate) {
        return BigDecimal.valueOf(ChronoUnit.DAYS.between(lastRateDate, date));
    }

    private Rate getLastRate(List<Rate> rates) {
        return rates.get(rates.size() - 1);
    }

    private List<Rate> getLastMonthSubList(List<Rate> rates) {
        return rates.subList(getIndexOfRateMonthBefore(rates), rates.size());
    }
}