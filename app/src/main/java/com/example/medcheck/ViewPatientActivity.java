package com.example.medcheck;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class ViewPatientActivity extends ActionBarActivity {

    private ArrayList<Task> tasks;
    private GregorianCalendar currentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        Bundle extras = getIntent().getExtras();
        String patientName = "";
        if (extras != null) {
            patientName = extras.getString("patient name");
        }

        final String patientEmail = patientName;

        currentCalendar = new GregorianCalendar();

        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://medcheck.firebaseio.com");
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "Username Here");
        final String email = preferences.getString("email", "Email Here");
        ImageButton addTaskButton = (ImageButton) findViewById(R.id.addTask);
        ImageButton addNoteButton = (ImageButton) findViewById(R.id.addNote);
        final Context context = this;
        ListView listView = (ListView) findViewById(R.id.doctorViewTasksList);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View alertView = li.inflate(R.layout.task_prompt, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(alertView);
                final EditText nameInput = (EditText) alertView.findViewById(R.id.nameInput);
                final EditText descInput = (EditText) alertView.findViewById(R.id.descInput);
                final EditText monthInput = (EditText) alertView.findViewById(R.id.monthInput);
                final EditText dayInput = (EditText) alertView.findViewById(R.id.dayInput);
                final EditText yearInput = (EditText) alertView.findViewById(R.id.yearInput);

                alert.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TODO: add to firebase
                                        Task newTask = new Task(nameInput.getText().toString().trim(), descInput.getText().toString().trim(),0);
                                        newTask.setDoctorEmail(email);
                                        newTask.setPatientEmail(patientEmail);
                                        TaskIndividual newTaskIndiv = new TaskIndividual(nameInput.getText().toString().trim(),
                                                new GregorianCalendar(Integer.parseInt(yearInput.getText().toString().trim()),Integer.parseInt(monthInput.getText().toString().trim())-1,Integer.parseInt(dayInput.getText().toString().trim())));
                                        newTask.getTaskList().add(newTaskIndiv);
                                        Firebase newUserRef = ref.child("tasks").child(nameInput.getText().toString().trim());
                                        newUserRef.setValue(newTask);

                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                alert.create().show();
            }
        });

        getTasks();
        StatTaskListAdapter adapter = new StatTaskListAdapter(this, R.layout.patient_item, tasks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(context, BarGraph.class);
                startActivity(intent);

            }
        });
    }

    public void getTasks() {
        tasks = new ArrayList<>();
        final Firebase ref = new Firebase("https://medcheck.firebaseio.com/tasks");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
