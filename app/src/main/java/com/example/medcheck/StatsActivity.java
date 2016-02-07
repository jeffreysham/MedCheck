package com.example.medcheck;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class StatsActivity extends ActionBarActivity {

    private GregorianCalendar currentCalendar;
    private ArrayList<Task> tasks;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        currentCalendar = new GregorianCalendar();
        listView = (ListView) findViewById(R.id.statTaskList);

        tasks = new ArrayList<>();
        getTasks();
        StatTaskListAdapter adapter = new StatTaskListAdapter(this, R.layout.stat_task_item, tasks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(context, PieChart.class);
                startActivity(intent);

            }
        });

    }

    public void getTasks() {
        /*Task temp = new Task("Check insulin levels", "Make sure your insulin levels are below 30g.", 0);
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 0));
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 3));
        tasks.add(temp);

        Task temp2 = new Task("Go for a run", "Target heart rate should be 150bpm", 0);
        GregorianCalendar other = new GregorianCalendar(2016, 1, 15);
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 0));
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 3));
        tasks.add(temp2);

        Task temp3 = new Task("Don't smoke", "Smoking is bad for your health", 0);
        GregorianCalendar other1 = new GregorianCalendar(2016, 1, 15);
        temp2.getTaskList().add(new TaskIndividual(temp3.getName(), other1, 0));
        temp2.getTaskList().add(new TaskIndividual(temp3.getName(), other1, 3));
        tasks.add(temp3);*/
    }
}
