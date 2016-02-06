package com.example.medcheck;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import android.widget.ListView;


public class StatsActivity extends ActionBarActivity {

    private GregorianCalendar currentCalendar;
    private ArrayList<Task> tasks;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        currentCalendar = new GregorianCalendar();
        listView = (ListView) findViewById(R.id.agendaTaskList);

        tasks = new ArrayList<>();
        getTasks();
        StatTaskListAdapter adapter = new StatTaskListAdapter(this, R.layout.stat_task_item, tasks);
        listView.setAdapter(adapter);
    }

    public void getTasks() {
        Task temp = new Task("Shots shots shots, Everybody!", "Medication Counter", 0);
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 0));
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 3));
        tasks.add(temp);

        Task temp2 = new Task("Run as fast as you can.", "exercise", 0);
        GregorianCalendar other = new GregorianCalendar(2016, 1, 15);
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 0));
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 3));
        tasks.add(temp2);

        Task temp3 = new Task("Nope.", "exercise", 0);
        GregorianCalendar other1 = new GregorianCalendar(2016, 1, 15);
        temp2.getTaskList().add(new TaskIndividual(temp3.getName(), other1, 0));
        temp2.getTaskList().add(new TaskIndividual(temp3.getName(), other1, 3));
        tasks.add(temp3);
    }
}
