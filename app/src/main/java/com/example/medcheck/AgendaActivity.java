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
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskIndividual.setStatistic(1);
                    }
                })
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
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
        Task temp = new Task("Exercise ", "Get 30 minutes of aerobic exercise a day.", 0);
        String date1 = "1/13/2016/12/30";
        String date2 = "1/16/2016/12/30";
        temp.getTaskList().add(new TaskIndividual(temp.getName(), date1, 0));
        temp.getTaskList().add(new TaskIndividual(temp.getName(), date2, 3));
        tasks.add(temp);

        Task temp2 = new Task("Take Insulin", "Take your R insulin at least 30 mins before eating.", 0);

        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), date1, 0));
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), date2, 3));
        tasks.add(temp2);
    }

    public void setupDates(ArrayList<Task> items) {

        for (int i = 0; i < items.size(); i++) {
            Task tempTask = items.get(i);
            List<TaskIndividual> indList = tempTask.getTaskList();

            for (int j = 0; j < indList.size(); j++) {
                TaskIndividual tempIndTask = indList.get(j);
                String[] array = tempIndTask.getDate().split("/");

                int month = Integer.parseInt(array[0]);

                if (month == currentCalendar.getTime().getMonth()) {
                    taskIndividuals.add(tempIndTask);
                }
            }
        }

    }
}
