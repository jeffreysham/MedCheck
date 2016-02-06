package com.example.medcheck;

import java.util.Date;

/**
 * Created by Joanna Kai on 2/5/2016.
 */
public class TaskIndividual {
    private Date date;
    private int statistic;

    public TaskIndividual(Date date, int statistic) {
        this.date = date;
        this.statistic = statistic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatistic() {
        return statistic;
    }

    public void setStatistic(int statistic) {
        this.statistic = statistic;
    }
}
