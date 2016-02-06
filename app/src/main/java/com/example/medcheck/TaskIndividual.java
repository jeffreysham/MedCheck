package com.example.medcheck;

import java.util.GregorianCalendar;

/**
 * Created by Joanna Kai on 2/5/2016.
 */
public class TaskIndividual {
    private String name;
    private GregorianCalendar date;
    private int statistic;

    public TaskIndividual(String name, GregorianCalendar date, int statistic) {
        this.name = name;
        this.date = date;
        this.statistic = statistic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public int getStatistic() {
        return statistic;
    }

    public void setStatistic(int statistic) {
        this.statistic = statistic;
    }
}
