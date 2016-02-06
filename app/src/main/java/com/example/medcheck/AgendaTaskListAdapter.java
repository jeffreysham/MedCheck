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
        TextView taskNameText;
        TextView taskNameDate;
        TextView taskNameDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder;
        TaskIndividual rowItem = taskIndividualList.get(position);
        LayoutInflater rowViewInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = rowViewInflater.inflate(R.layout.agenda_task_item, null);
            holder = new TaskViewHolder();
            holder.taskNameText = (TextView) convertView.findViewById(R.id.taskName);
            holder.taskNameDate = (TextView) convertView.findViewById(R.id.taskTime);
            holder.taskNameDesc = (TextView) convertView.findViewById(R.id.taskDesc);
            convertView.setTag(holder);
        } else {
            holder = (TaskViewHolder) convertView.getTag();
        }
        if (rowItem != null) {
            holder.taskNameText.setText(rowItem.getName());
            holder.taskNameDate.setText(rowItem.getDate().get(Calendar.HOUR_OF_DAY) + " " + rowItem.getDate().get(Calendar.AM_PM));
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
}
