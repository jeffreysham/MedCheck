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
    private String patientEmail;
    private String doctorEmail;

    public Task(String name, String description, int frequency) {
        this.name = name;
        this.taskList = new ArrayList<>();
        this.description = description;
        this.frequency = frequency;
        this.patientEmail = "";
        this.doctorEmail = "";
    }

    public Task(String name, String description, int frequency, String patientEmail, String doctorEmail) {
        this.name = name;
        this.taskList = new ArrayList<>();
        this.description = description;
        this.frequency = frequency;
        this.patientEmail = patientEmail;
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
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
