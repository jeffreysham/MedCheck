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
public class TaskListAdapter extends ArrayAdapter<TaskIndividual> {
    private Context context;
    private List<TaskIndividual> taskIndividualList;

    public TaskListAdapter(Context context, int resource, List<TaskIndividual> items) {
        super(context, resource, items);
        this.context = context;
        this.taskIndividualList = items;
    }

    private class TaskViewHolder {
        TextView taskNameText;
        TextView taskNameDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder;
        TaskIndividual rowItem = taskIndividualList.get(position);
        LayoutInflater rowViewInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = rowViewInflater.inflate(R.layout.task_item, null);
            holder = new TaskViewHolder();
            holder.taskNameText = (TextView) convertView.findViewById(R.id.taskName);
            holder.taskNameDate = (TextView) convertView.findViewById(R.id.taskTime);
            convertView.setTag(holder);
        } else {
            holder = (TaskViewHolder) convertView.getTag();
        }
        if (rowItem != null) {
            holder.taskNameText.setText(rowItem.getName());
            holder.taskNameDate.setText(formatTime(rowItem.getDate().get(Calendar.HOUR_OF_DAY),rowItem.getDate().get(Calendar.MINUTE)));

        }
        return convertView;
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
