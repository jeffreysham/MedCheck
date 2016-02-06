package com.example.medcheck;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.medcheck.RobotoCalendarView;
import com.example.medcheck.RobotoCalendarView.RobotoCalendarListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CalendarActivity extends ActionBarActivity implements RobotoCalendarListener{
    private RobotoCalendarView robotoCalendarView;
    private int currentMonthIndex;
    private GregorianCalendar currentCalendar;
    private ArrayList<Task> tasks;
    private ArrayList<TaskIndividual> taskIndividuals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        robotoCalendarView = (RobotoCalendarView) findViewById(R.id.calendarPicker);

        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        // Initialize the RobotoCalendarPicker with the current index and date
        currentMonthIndex = 0;
        currentCalendar = new GregorianCalendar();

        // Mark current day
        robotoCalendarView.markDayAsCurrentDay(currentCalendar.getTime());

        tasks = new ArrayList<>();
        taskIndividuals = new ArrayList<>();
        getDates();
        setupDates(tasks);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void getDates() {
        Task temp = new Task("Eat YOUR PILLS!!!!!", "Medication Counter", 0);
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 0));
        temp.getTaskList().add(new TaskIndividual(temp.getName(), currentCalendar, 3));
        tasks.add(temp);

        Task temp2 = new Task("RUN!!!!!", "exercise", 0);
        GregorianCalendar other = new GregorianCalendar(2016, 2, 15);
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 0));
        temp2.getTaskList().add(new TaskIndividual(temp2.getName(), other, 3));
        tasks.add(temp2);
    }

    public void setupDates(ArrayList<Task> items) {

        for (int i = 0; i < items.size(); i++) {
            Task tempTask = items.get(i);
            List<TaskIndividual> indList = tempTask.getTaskList();

            for (int j = 0; i < indList.size(); i++) {
                TaskIndividual tempIndTask = indList.get(j);
                Log.i("test", tempIndTask.getName() + " " + tempIndTask.getDate());
                Log.i("test", currentCalendar.get(Calendar.MONTH) + "");

                if (tempIndTask.getDate().getTime().getMonth() == currentCalendar.getTime().getMonth()) {
                    robotoCalendarView.markFirstUnderlineWithStyle(RobotoCalendarView.BLUE_COLOR, tempIndTask.getDate().getTime());
                    taskIndividuals.add(tempIndTask);
                }
            }
        }

    }

    @Override
    public void onDateSelected(Date date) {
        LayoutInflater li = LayoutInflater.from(this);
        View alertView = li.inflate(R.layout.task_item_alert, null);
        AlertDialog.Builder dateInfoAlert = new AlertDialog.Builder(this);
        dateInfoAlert.setView(alertView);
        ListView taskListView = (ListView) alertView.findViewById(R.id.taskList);
        TaskListAdapter taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskIndividuals);
        taskListView.setAdapter(taskListAdapter);
        dateInfoAlert.create().show();
    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        updateCalendar();
    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        updateCalendar();
    }

    private void updateCalendar() {
        currentCalendar = new GregorianCalendar();
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        robotoCalendarView.initializeCalendar(currentCalendar);
        tasks = new ArrayList<>();
        taskIndividuals = new ArrayList<>();
        getDates();
        setupDates(tasks);
    }
}
