package com.exchangeForecast.parser;

import com.exchangeForecast.domain.Rate;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RatesParserTest {

    @Test
    public void shouldParseRateRow() {
        RatesParser ratesParser = new RatesParser();
        String rateRow = "1;12.02.2022;\"74,9867\";Äמככאנ ÑØÀ";
        Rate rate = ratesParser.parseRateRow(rateRow);
        System.out.println(rate);
    }
}