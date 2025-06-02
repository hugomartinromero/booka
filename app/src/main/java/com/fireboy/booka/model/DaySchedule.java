package com.fireboy.booka.model;

import java.util.List;

public class DaySchedule {
    private List<String> available;

    public DaySchedule() {
    }

    public DaySchedule(List<String> available) {
        this.available = available;
    }

    public List<String> getAvailable() {
        return available;
    }

    public void setAvailable(List<String> available) {
        this.available = available;
    }
}
