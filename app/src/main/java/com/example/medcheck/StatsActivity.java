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
    private ArrayList<TaskIndividual> taskIndividuals;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        currentCalendar = new GregorianCalendar();
        listView = (ListView) findViewById(R.id.agendaTaskList);

        tasks = new ArrayList<>();
        taskIndividuals = new ArrayList<>();
        getDates();
        setupDates(tasks);
        Collections.sort(taskIndividuals);
        AgendaTaskListAdapter adapter = new AgendaTaskListAdapter(this, R.layout.agenda_task_item, taskIndividuals, tasks);
        listView.setAdapter(adapter);
    }

    public void getDates() {
        Task temp = new Task("Eat YOUR PILLS!!!!!", "Medication Counter", 0);
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 0));
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 3));
        tasks.add(temp);

        Task temp2 = new Task("RUN!!!!!", "exercise", 0);
        GregorianCalendar other = new GregorianCalendar(2016, 1, 15);
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 0));
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 3));
        tasks.add(temp2);
    }

    public void setupDates(ArrayList<Task> items) {

        for (int i = 0; i < items.size(); i++) {
            Task tempTask = items.get(i);
            List<TaskIndividual> indList = tempTask.getTaskList();

            for (int j = 0; j < indList.size(); j++) {
                TaskIndividual tempIndTask = indList.get(j);

                if (tempIndTask.getDate().getTime().getMonth() == currentCalendar.getTime().getMonth()) {
                    taskIndividuals.add(tempIndTask);
                }
            }
        }

    }
}
