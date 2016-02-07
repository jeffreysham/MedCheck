package com.example.medcheck;

/**
 * Created by Erica on 2/6/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class BarGraph extends Activity {
    float values[] = {1, 2, 4, 8};

    public void drawPie(View view) {
        Intent intent = new Intent(this, PieChart.class);
        startActivity(intent);
    }

    public void drawBar(View view) {
        Intent intent = new Intent(this, BarGraph.class);
        startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        final String email = preferences.getString("email", "Email Here");
        final ArrayList<Task> tasks = new ArrayList<>();

        final Firebase ref = new Firebase("https://medcheck.firebaseio.com/tasks");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String patientEmail = (String) dataSnapshot.child("patientEmail").getValue();
                if (patientEmail.equals(email)) {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        RelativeLayout bar = (RelativeLayout) findViewById(R.id.bar);
        BarChart our_chart = new BarChart(this, values);
        bar.addView(our_chart);

    }

    public class BarChart extends View {
        Paint textPaint;
        Paint linePaint;
        Paint boxPaint[];
        Paint textPaintBox[];
        double cost;
        double earnings;
        float scaleFactor;

        public BarChart(Context context, float[] values) {
            super(context);

            int r = 85;
            int g = 198;
            int b = 218;

            int step = (255 - 218) / values.length;

            textPaintBox = new Paint[values.length];
            for (int i = 0; i < values.length; i++) {
                textPaintBox[i] = new Paint();
            }
            boxPaint = new Paint[values.length];
            for (int i = 0; i < values.length; i++) {
                boxPaint[i] = new Paint();
            }

            initialise();
        }

        void initialise() {

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            scaleFactor = metrics.density;

            textPaint = new Paint();
            linePaint = new Paint();

            linePaint.setStrokeWidth(1);
            linePaint.setColor(0xFFC5C5C5);
            textPaint.setColor(0xFFC5C5C5);
            textPaint.setTextSize(14 * scaleFactor);

            int r = 85;
            int g = 198;
            int b = 218;

            int step = (255 - 218) / boxPaint.length;

            for (int i = 0; i < boxPaint.length; i++) {

                textPaintBox[i].setColor(0xFF2D9EB2);
                boxPaint[i].setColor(Color.rgb(r, g, b));
                r = r + step;
                g = g + step;
                b = b + step;
                textPaintBox[i].setTextSize(10 * scaleFactor);
            }
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int fullWidth = getWidth();
            int fullHeight = getHeight() - 225;
            int padding = (int) (10 * scaleFactor);
            int maxBarHeight = fullHeight - 10 * padding;
            float bar_height[] = new float[boxPaint.length];
            float max_height = 0;

            for (int i = 0; i < boxPaint.length; i++) {
                if (values[i] > max_height) {
                    max_height = values[i];
                }
            }

            for (int i = 0; i < boxPaint.length; i++) {

                bar_height[i] = (float) ((values[i] / max_height) * maxBarHeight);
            }

            canvas.drawLine(padding, fullHeight - 25 * scaleFactor, fullWidth - padding, fullHeight - 25 * scaleFactor, linePaint);


            for (int i = 0; i < boxPaint.length; i++) {
                float barbottom = fullHeight - padding * 3;
                float bartop = barbottom - bar_height[i];
                float valpos = fullHeight - 10;
                canvas.drawRect((1f / boxPaint.length) * i * fullWidth + padding, bartop, (1f / boxPaint.length) * (i + 1) * fullWidth - padding, barbottom, boxPaint[i]);
                canvas.drawText("Week " + (i + 1), (1f / boxPaint.length) * i * fullWidth + (1f / (boxPaint.length * 2)) * fullWidth - 35, valpos, textPaintBox[i]);
                canvas.drawText("" + values[i], (1f / boxPaint.length) * i * fullWidth + (1f / (boxPaint.length * 2)) * fullWidth - 15, bartop - 10, textPaintBox[i]);
            }

        }

    }
}