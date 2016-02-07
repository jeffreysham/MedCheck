package com.example.medcheck;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class NoteActivity extends ActionBarActivity {

    private GregorianCalendar currentCalendar;
    private ArrayList<Task> tasks;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_note);

        currentCalendar = new GregorianCalendar();
        listView = (ListView) findViewById(R.id.noteTaskList);

        tasks = new ArrayList<>();
        getTasks();
        StatTaskListAdapter adapter = new StatTaskListAdapter(this, R.layout.note_task_item, tasks);
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
        final Task task = (Task) l.getItemAtPosition(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(task.getName())
                .setMessage(task.getDescription())
                .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void getTasks() {

        Task temp = new Task("Exercise ", "Get 30 minutes of aerobic exercise a day.", 0);
        String date1 = "1/13/2016/12/30";
        temp.getTaskList().add(new TaskIndividual(temp.getName(), date1, 0));
        temp.getTaskList().add(new TaskIndividual(temp.getName(), date1, 3));
        tasks.add(temp);

        Task temp2 = new Task("Take Insulin", "Take your R insulin at least 30 mins before eating.", 0);
        String date2 = "1/16/2016/12/30";
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), date2, 0));
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), date2, 3));
        tasks.add(temp2);
    }
}
