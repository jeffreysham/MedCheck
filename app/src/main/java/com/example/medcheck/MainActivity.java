package com.example.medcheck;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton calendarButton = (ImageButton) findViewById(R.id.calendar);
        ImageButton agendaButton = (ImageButton) findViewById(R.id.agenda);
        ImageButton statButton = (ImageButton) findViewById(R.id.stats);
        ImageButton noteButton = (ImageButton) findViewById(R.id.notes);
        final Context context = this;
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CalendarActivity.class);
                startActivity(intent);
            }
        });

        agendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AgendaActivity.class);
                startActivity(intent);
            }
        });

        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatsActivity.class);
                startActivity(intent);
            }
        });

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                startActivity(intent);
            }
        });

        TextView userGreetingView = (TextView) findViewById(R.id.userGreetingText);
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "Username Here");
        userGreetingView.setText("Hello, " + name);

        // Dummy tasks
        Task AccutanePills = new Task("Don't forget to:\nTake Accutane", "For Acne", 1);
        List<TaskIndividual> AccutanePillList = new ArrayList<>();
        for (int i=0; i<30; i++) {
            String day = "Day " + Integer.toString(i);
            TaskIndividual temp = new TaskIndividual(day, new GregorianCalendar(2016,2,i,1,30));
            AccutanePillList.add(temp);
        }
        AccutanePills.setTaskList(AccutanePillList);

        Task StopSmoking = new Task("Stop Smoking", "For smokers", 1);
        List<TaskIndividual> SmokingList = new ArrayList<>();
        for (int i=0; i<30; i++) {
            String day = "Day " + Integer.toString(i);
            TaskIndividual temp = new TaskIndividual(day, new GregorianCalendar(2016,2,i,1,30));
            SmokingList.add(temp);
        }
        StopSmoking.setTaskList(SmokingList);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(AccutanePills);
        tasks.add(StopSmoking);

        final TextView mainActTaskName = (TextView) findViewById(R.id.mainActTaskName);
        mainActTaskName.setText(tasks.get(0).getName());

        final TextView mainActTaskTime = (TextView) findViewById(R.id.mainActTaskTime);
        mainActTaskTime.setText("at " + formatTime(tasks.get(0).getTaskList().get(15).getDate().get(Calendar.HOUR_OF_DAY),
                tasks.get(0).getTaskList().get(15).getDate().get(Calendar.MINUTE)));

        // Already done button
        final Button mainActDoneButton = (Button) findViewById(R.id.mainActDoneButton);

        // Not today button
        final Button mainActNotDoneButton = (Button) findViewById(R.id.mainActNotDoneButton);

        // Button operations
        mainActDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Warning").setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // if yes, adjust statistic for the day. currently only day 15 of current month
                                tasks.get(count).getTaskList().get(15).setStatistic(1);
                                count++;
                                if (count == tasks.size()) {
                                    mainActTaskName.setText("Done tasks!");
                                    mainActTaskTime.setText("");
                                    mainActDoneButton.setEnabled(false);
                                    mainActNotDoneButton.setEnabled(false);
                                } else {
                                    mainActTaskName.setText(tasks.get(count).getName());
                                    mainActTaskTime.setText("at " + formatTime(tasks.get(count).getTaskList().get(15).getDate().get(Calendar.HOUR_OF_DAY),
                                            tasks.get(count).getTaskList().get(15).getDate().get(Calendar.MINUTE)));
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });

        mainActNotDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Warning").setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // if yes, adjust statistic for the day. currently only day 15 of current month
                                tasks.get(count).getTaskList().get(15).setStatistic(0);
                                count++;
                                if (count == tasks.size()) {
                                    mainActTaskName.setText("Done tasks!");
                                    mainActTaskTime.setText("");
                                    mainActDoneButton.setEnabled(false);
                                    mainActNotDoneButton.setEnabled(false);
                                } else {
                                    mainActTaskName.setText(tasks.get(count).getName());
                                    mainActTaskTime.setText("at " + formatTime(tasks.get(count).getTaskList().get(15).getDate().get(Calendar.HOUR_OF_DAY),
                                            tasks.get(count).getTaskList().get(15).getDate().get(Calendar.MINUTE)));
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });
    }

    public String formatTime(int hours, int minutes) {
        String hourString = "";
        String minuteString = "";
        if (hours < 10) {
            hourString = "0";
        }
        hourString += hours;
        if (minutes < 10) {
            minuteString = "0";
        }
        minuteString += minutes;
        return hourString + ":" + minuteString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
