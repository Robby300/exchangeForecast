package com.exchangeForecast;

import com.exchangeForecast.parsers.CommandParser;
import com.exchangeForecast.ui.UserInterface;

import java.io.FileNotFoundException;

public class Main {

    private static final UserInterface userInterface = new UserInterface();


    public static void main(String[] args) throws FileNotFoundException {

            userInterface.initialize();

    }
}
