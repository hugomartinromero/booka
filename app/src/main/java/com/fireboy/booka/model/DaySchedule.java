package com.fireboy.booka.model;

public class DaySchedule {
    private String open;
    private String close;

    public DaySchedule() {
    }

    public DaySchedule(String open, String close) {
        this.open = open;
        this.close = close;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }
}