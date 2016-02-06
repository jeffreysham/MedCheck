package com.example.medcheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
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

        TextView userGreetingView = (TextView) findViewById(R.id.userGreetingText);
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "Username Here");
        userGreetingView.setText("Hello, " + name);

        // Already done button
        Button mainActDoneButton = (Button) findViewById(R.id.mainActDoneButton);
        mainActDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Fix this
                Intent intent = new Intent(context, CalendarActivity.class);
                startActivity(intent);
            }
        });

        // Not today button
        Button mainActNotDoneButton = (Button) findViewById(R.id.mainActNotDoneButton);
        mainActNotDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Fix this
                Intent intent = new Intent(context, CalendarActivity.class);
                startActivity(intent);
            }
        });


        Task AccutanePills = new Task("Take Accutane", "For Acne", 1);
        List<TaskIndividual> AccutanePillList = new ArrayList<>();
        for (int i=0; i<30; i++) {
            String day = "Day " + Integer.toString(i);
            TaskIndividual temp = new TaskIndividual(day, new GregorianCalendar(2016, 2, i));
            AccutanePillList.add(temp);
        }
        AccutanePills.setTaskList(AccutanePillList);

        TextView mainActTaskName = (TextView) findViewById(R.id.mainActTaskName);
        mainActTaskName.setText(AccutanePills.getName());

        TextView mainActTaskTime = (TextView) findViewById(R.id.mainActTaskTime);
        //mainActTaskTime.setText("at " + formatTime(AccutanePills.getTaskList().get(3).getDate().get(Calendar.HOUR_OF_DAY), AccutanePills.getTaskList().get(3).getDate().get(Calendar.MINUTE)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void drawPie(View view) {
        Intent intent = new Intent(this, PieChart.class);
        startActivity(intent);
    }

    public void drawBar(View view) {
        Intent intent = new Intent(this, MakeBar.class);
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
