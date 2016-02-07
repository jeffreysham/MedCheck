package com.example.medcheck;

/**
 * Created by Joanna Kai on 2/5/2016.
 */
public class TaskIndividual implements Comparable<TaskIndividual>{
    private String name;
    private String date;
    private int statistic;

    public TaskIndividual(String name, String date, int statistic) {
        this.name = name;
        this.date = date;
        this.statistic = statistic;
    }

    public TaskIndividual(String name, String date) {
        this.name = name;
        this.date = date;
        this.statistic = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatistic() {
        return statistic;
    }

    public void setStatistic(int statistic) {
        this.statistic = statistic;
    }

    @Override
    public int compareTo(TaskIndividual another) {
        return this.getDate().compareTo(another.getDate());
    }
}
