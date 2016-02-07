package com.example.medcheck;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Joanna Kai on 2/6/2016.
 */
public class AgendaTaskListAdapter extends ArrayAdapter<TaskIndividual> {
    private Context context;
    private List<TaskIndividual> taskIndividualList;
    private List<Task> tasks;

    public AgendaTaskListAdapter(Context context, int resource, List<TaskIndividual> items, List<Task> tasks) {
        super(context, resource, items);
        this.context = context;
        this.taskIndividualList = items;
        this.tasks = tasks;
    }

    private class TaskViewHolder {
        TextView taskNameDate;
        TextView taskNameText;
        TextView taskNameTime;
        TextView taskNameDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder;
        TaskIndividual rowItem = taskIndividualList.get(position);
        LayoutInflater rowViewInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = rowViewInflater.inflate(R.layout.agenda_task_item, null);
            holder = new TaskViewHolder();
            holder.taskNameDate = (TextView) convertView.findViewById(R.id.taskDate);
            holder.taskNameText = (TextView) convertView.findViewById(R.id.taskName);
            holder.taskNameTime = (TextView) convertView.findViewById(R.id.taskTime);
            holder.taskNameDesc = (TextView) convertView.findViewById(R.id.taskDesc);
            convertView.setTag(holder);
        } else {
            holder = (TaskViewHolder) convertView.getTag();
        }
        if (rowItem != null) {

            String[] array = rowItem.getDate().split("/");
            int month = Integer.parseInt(array[0]);
            int day = Integer.parseInt(array[1]);
            int theNewMins = Integer.parseInt(array[4]);
            int hourTime = Integer.parseInt(array[3]);

            holder.taskNameDate.setText(month + " " + day + "");
            holder.taskNameText.setText(rowItem.getName());
            holder.taskNameTime.setText(formatTime(hourTime,theNewMins));;
            holder.taskNameDesc.setText(getDesc(rowItem));
        }
        return convertView;
    }

    private String getDesc(TaskIndividual taskIndividual) {
        for (int i = 0; i < tasks.size(); i++) {
            Task temp = tasks.get(i);
            if (taskIndividual.getName().equals(temp.getName())) {
                return temp.getDescription();
            }
        }
        return "";
    }

    private String getMonthString(int month) {
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "";
        }
    }

    private String formatTime(int hours, int minutes) {
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
        return hourString+":"+minuteString;

    }
}
