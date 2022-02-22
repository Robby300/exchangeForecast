package com.exchangeForecast;

import com.exchangeForecast.domens.Currency;
import com.exchangeForecast.domens.Rate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final String EURO_RATE_FILE = "src/main/resources/static/EUR_F01_02_2002_T01_02_2022.csv";
    private static final String LIRAPATHLINE = "src/main/resources/static/TRY_F01_02_2002_T01_02_2022.csv";
    private static final String DOLLARPATHLINE = "src/main/resources/static/USD_F01_02_2002_T01_02_2022.csv";

    private static Rate parseRateRow(String rateRow) {
        String[] rateParts = rateRow.split(";");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Rate rate = new Rate(LocalDate.parse(rateParts[0], formatter), Double.parseDouble(rateParts[1].replace(",", ".")), Currency.EUR);
        return rate;
    }

    private static List<Rate> getRates(String filePath) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        List<String> rateRows = bufferedReader.lines().skip(1).collect(Collectors.toList());
        Collections.reverse(rateRows);
        List<Rate> rates = rateRows.stream().map(Main::parseRateRow).collect(Collectors.toList());
        return rates;
    }


    public static void main(String[] args) throws FileNotFoundException {

        List<Rate> rateList = getRates(EURO_RATE_FILE);
        int MAXN = rateList.size();
        int n = 0;
        int[] days = new int[MAXN];
        double[] exchangeRate = new double[MAXN];

        // first pass: read in data, compute xbar and ybar
        double sumDays = 0.0, sumExchangeRates = 0.0, sumSquareDays = 0.0;

        days = IntStream.range(0, rateList.size()).toArray();
        sumDays = Arrays.stream(days).sum();
        sumSquareDays = Arrays.stream(days).map(day -> day * day).sum();
        sumExchangeRates = rateList.stream().mapToDouble(Rate::getExchangeRate)
                .sum();

        for (Rate rate : rateList) {
            days[n] = n;
            exchangeRate[n] = rate.getExchangeRate();
            sumDays += days[n];
            sumSquareDays += days[n] * days[n];
            sumExchangeRates += exchangeRate[n];
            n++;
        }
        double xbar = sumDays / n;
        double ybar = sumExchangeRates / n;

        // second pass: compute summary statistics
        double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        for (int i = 0; i < n; i++) {
            xxbar += (days[i] - xbar) * (days[i] - xbar);
            yybar += (exchangeRate[i] - ybar) * (exchangeRate[i] - ybar);
            xybar += (days[i] - xbar) * (exchangeRate[i] - ybar);
        }
        double beta1 = xybar / xxbar;
        double beta0 = ybar - beta1 * xbar;

        // print results
        System.out.println("exchangeRate   = " + beta1 + " * days + " + beta0);

        // analyze results
        int df = n - 2;
        double rss = 0.0;      // residual sum of squares
        double ssr = 0.0;      // regression sum of squares
        for (int i = 0; i < n; i++) {
            double fit = beta1 * days[i] + beta0;
            rss += (fit - exchangeRate[i]) * (fit - exchangeRate[i]);
            ssr += (fit - ybar) * (fit - ybar);
        }
        double R2 = ssr / yybar;
        double svar = rss / df;
        double svar1 = svar / xxbar;
        double svar0 = svar / n + xbar * xbar * svar1;
        System.out.println("R^2                 = " + R2);
        System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
        svar0 = svar * sumSquareDays / (n * xxbar);
        System.out.println("std error of beta_0 = " + Math.sqrt(svar0));

        System.out.println("SSTO = " + yybar);
        System.out.println("SSE  = " + rss);
        System.out.println("SSR  = " + ssr);
        System.out.println(rateList);
        System.out.println(rateList.size());
        System.out.println(beta1 * 11 +  beta0);
    }
}
