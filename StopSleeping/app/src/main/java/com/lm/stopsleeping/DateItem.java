package com.lm.stopsleeping;

public class DateItem { // getter and setter
    private int id;
    private String sleepDate;   // sleep 시간

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateItem() {
    }

    public String getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(String sleepDate) {
        this.sleepDate = sleepDate;
    }
}
