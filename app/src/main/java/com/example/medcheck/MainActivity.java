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

        Task AccutanePills = new Task("Take Accutane", "For Acne", 1);
        List<TaskIndividual> AccutanePillList = new ArrayList<>();
        for (int i=0; i<30; i++) {
            String day = "Day " + Integer.toString(i);
            TaskIndividual temp = new TaskIndividual(day, new GregorianCalendar(2016,2,i,1,30));
            AccutanePillList.add(temp);
        }
        AccutanePills.setTaskList(AccutanePillList);

        final Task temp = AccutanePills;

        TextView mainActTaskName = (TextView) findViewById(R.id.mainActTaskName);
        mainActTaskName.setText(temp.getName());

        TextView mainActTaskTime = (TextView) findViewById(R.id.mainActTaskTime);
        mainActTaskTime.setText("at " + formatTime(temp.getTaskList().get(3).getDate().get(Calendar.HOUR_OF_DAY), temp.getTaskList().get(3).getDate().get(Calendar.MINUTE)));

        // Already done button
        Button mainActDoneButton = (Button) findViewById(R.id.mainActDoneButton);
        mainActDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Warning").setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // if yes, adjust statistic for the day. currently only day 15 of current month
                                temp.getTaskList().get(15).setStatistic(1);
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });

        // Not today button
        Button mainActNotDoneButton = (Button) findViewById(R.id.mainActNotDoneButton);
        mainActNotDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Warning").setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // if yes, adjust statistic for the day. currently only day 15 of current month
                                temp.getTaskList().get(15).setStatistic(0);
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
