package com.exchangeForecast.domain;

public class Command {
    private final String firstCommand;
    private String cdx;
    private String period;

    public Command(String firstCommand) {
        this.firstCommand = firstCommand;
    }

    public Command(String firstCommand, String cdx, String period) {
        this.firstCommand = firstCommand;
        this.cdx = cdx;
        this.period = period;
    }

    public String getFirstCommand() {
        return firstCommand;
    }

    public String getCdx() {
        return cdx;
    }

    public void setCdx(String cdx) {
        this.cdx = cdx;
    }

    public String getPeriod() {
        return period;
    }
}
