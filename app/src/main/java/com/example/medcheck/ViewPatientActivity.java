package com.example.medcheck;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class ViewPatientActivity extends ActionBarActivity {

    private ArrayList<Task> tasks;
    private GregorianCalendar currentCalendar;
    private String thePatientEmail;
    private Context context = this;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        Bundle extras = getIntent().getExtras();
        String temppatientEmail = "";
        String patientName = "";
        if (extras != null) {
            temppatientEmail = extras.getString("patient email");
            patientName = extras.getString("patient name");

        }

        TextView patientNameView = (TextView) findViewById(R.id.doctorViewPatientName);
        patientNameView.setText(patientName);

        thePatientEmail = temppatientEmail;

        currentCalendar = new GregorianCalendar();

        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://medcheck.firebaseio.com");
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "Username Here");
        final String email = preferences.getString("email", "Email Here");
        ImageButton addTaskButton = (ImageButton) findViewById(R.id.addTask);

        final Context context = this;
        listView = (ListView) findViewById(R.id.doctorViewTasksList);

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
                final EditText hoursInput = (EditText) alertView.findViewById(R.id.hourInput);
                final EditText minutesInput = (EditText) alertView.findViewById(R.id.minsInput);

                alert.setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //TODO: add to firebase
                                        Task newTask = new Task(nameInput.getText().toString().trim(), descInput.getText().toString().trim(), 0);
                                        newTask.setDoctorEmail(email);
                                        newTask.setPatientEmail(thePatientEmail);
                                        TaskIndividual newTaskIndiv = new TaskIndividual(nameInput.getText().toString().trim(),
                                                new GregorianCalendar(Integer.parseInt(yearInput.getText().toString().trim()), Integer.parseInt(monthInput.getText().toString().trim()) - 1, Integer.parseInt(dayInput.getText().toString().trim())));
                                        newTask.getTaskList().add(newTaskIndiv);
                                        Firebase newUserRef = ref.child("tasks").child(nameInput.getText().toString().trim());
                                        newUserRef.setValue(newTask);

                                        newUserRef.child("day").setValue(Integer.parseInt(dayInput.getText().toString().trim()));
                                        newUserRef.child("month").setValue(Integer.parseInt(monthInput.getText().toString().trim()));
                                        newUserRef.child("year").setValue(Integer.parseInt(yearInput.getText().toString().trim()));
                                        newUserRef.child("hour").setValue(Integer.parseInt(hoursInput.getText().toString().trim()));
                                        newUserRef.child("mins").setValue(Integer.parseInt(minutesInput.getText().toString().trim()));

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

    }

    public void getTasks() {
        tasks = new ArrayList<>();
        final Firebase ref = new Firebase("https://medcheck.firebaseio.com/tasks");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("testing", "Patient Email: " + thePatientEmail);

                String patientEmail = (String) dataSnapshot.child("patientEmail").getValue();
                Log.i("testing2", "Patient Email: " + patientEmail);
                Log.i("testing3", dataSnapshot.toString());
                if (patientEmail.equals(thePatientEmail)) {

                    String taskName = (String) dataSnapshot.child("name").getValue();
                    String desc = (String) dataSnapshot.child("description").getValue();
                    String doctorEmail = (String) dataSnapshot.child("doctorEmail").getValue();
                    int frequency = Integer.parseInt(dataSnapshot.child("frequency").getValue() + "");

                    int statistic = Integer.parseInt(dataSnapshot.child("taskList").child("0").child("statistic").getValue() + "");
                    int day = Integer.parseInt(dataSnapshot.child("day").getValue() + "");
                    int month = Integer.parseInt(dataSnapshot.child("month").getValue() + "");
                    int year = Integer.parseInt(dataSnapshot.child("year").getValue() + "");
                    int hour = Integer.parseInt(dataSnapshot.child("hour").getValue() + "");
                    int mins = Integer.parseInt(dataSnapshot.child("mins").getValue() + "");
                    GregorianCalendar date = new GregorianCalendar(year, month, day, hour, mins);

                    TaskIndividual taskIndividual = new TaskIndividual(taskName, date, statistic);

                    Task task = new Task(taskName, desc, frequency, patientEmail, doctorEmail);
                    task.getTaskList().add(taskIndividual);
                    tasks.add(task);
                    StatTaskListAdapter adapter = new StatTaskListAdapter(context, R.layout.stat_task_item, tasks);
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
