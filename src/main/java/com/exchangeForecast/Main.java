package com.exchangeForecast;

import java.io.*;
import java.util.stream.Stream;

public class Main {
    private static final String EUROPATHLINE = "src/main/resources/static/EUR_F01_02_2002_T01_02_2022.csv";
    private static final String LIRAPATHLINE = "src/main/resources/static/TRY_F01_02_2002_T01_02_2022.csv";
    private static final String DOLLARPATHLINE = "src/main/resources/static/USD_F01_02_2002_T01_02_2022.csv";

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(EUROPATHLINE));
        bufferedReader.lines().forEach(line -> System.out.println(line));

    }

}
