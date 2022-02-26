package com.exchangeForecast.command;

import com.exchangeForecast.domain.Currency;
import com.exchangeForecast.domain.Rate;
import com.exchangeForecast.parsers.RatesParser;
import com.exchangeForecast.service.AverageForecastService;
import com.exchangeForecast.service.ForecastService;

import java.util.List;

public class RateCommand implements Command {
    private static final String COMMAND_NAME = "rate";
    private Currency cdx;
    private String period;
    private final RatesParser ratesParser = new RatesParser();
    private final ForecastService service = new AverageForecastService();

    public RateCommand(Builder builder) {
        this.cdx = builder.cdx;
        this.period = builder.period;
    }

    @Override
    public void execute() {
        List<Rate> ratesByCDX = ratesParser.getRatesByCDX(cdx);
        if (period.equals("tomorrow")) {
            System.out.println(service.forecastTomorrow(ratesByCDX));
        } else if (period.equals("week")) {
            List<Rate> rates = service.forecastNextWeek(ratesByCDX);
            rates.forEach(System.out::println);
        } else {
            System.err.println("wrong period!");
        }
    }

    public static class Builder {
        private Currency cdx;
        private String period;

        public Builder cdx(Currency cdx) {
            this.cdx = cdx;
            return this;
        }

        public Builder period(String period) {
            this.period = period;
            return this;
        }

        public RateCommand build() {
            return new RateCommand(this);
        }
    }
}
