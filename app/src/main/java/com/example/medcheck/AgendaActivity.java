package com.example.medcheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class AgendaActivity extends ActionBarActivity {

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
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleItemClick(listView, view, position, id);
            }
        });
    }

    private void handleItemClick(ListView l, View v, int position, long id) {
        final TaskIndividual taskIndividual = (TaskIndividual) l.getItemAtPosition(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Enter Task Information")
                .setMessage("Did you " + taskIndividual.getName() + "?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskIndividual.setStatistic(1);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskIndividual.setStatistic(0);
                    }
                });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
