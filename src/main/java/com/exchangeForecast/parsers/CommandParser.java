package com.exchangeForecast.parsers;

import java.util.Scanner;

public class CommandParser {
    private String cdx;
    private String period;
    private Scanner scanner = new Scanner(System.in);

    public String[] parse() {
        String command = scanner.nextLine();
        scanner.close();
        String[] commandParts = command.split("\\s");
        return commandParts;
    }
}
