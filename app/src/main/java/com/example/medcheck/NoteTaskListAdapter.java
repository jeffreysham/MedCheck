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
public class NoteTaskListAdapter extends ArrayAdapter<Task> {
    private Context context;
    private List<Task> tasks;

    public NoteTaskListAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.tasks = tasks;
    }
    private class TaskViewHolder {
        TextView taskNameText;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder;
        Task rowItem = tasks.get(position);
        LayoutInflater rowViewInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = rowViewInflater.inflate(R.layout.stat_task_item, null);
            holder = new TaskViewHolder();
            holder.taskNameText = (TextView) convertView.findViewById(R.id.taskName);
            convertView.setTag(holder);
        } else {
            holder = (TaskViewHolder) convertView.getTag();
        }
        if (rowItem != null) {
            holder.taskNameText.setText(rowItem.getName());
        }
        return convertView;
    }

}