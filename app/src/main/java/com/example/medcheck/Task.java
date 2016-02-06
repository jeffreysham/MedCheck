package com.example.medcheck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joanna Kai on 2/5/2016.
 */
public class Task {
    private String name;
    private List<TaskIndividual> taskList;
    private String description;
    private int frequency;

    public Task(String name, String description, int frequency) {
        this.name = name;
        this.taskList = new ArrayList<>();
        this.description = description;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskIndividual> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskIndividual> taskList) {
        this.taskList = taskList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
