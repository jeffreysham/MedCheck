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
    float values[];
    Context context = this;
    String email;
    public void goStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void drawPie(View view) {
        Intent intent = new Intent(this, PieChart.class);
        startActivity(intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        Firebase.setAndroidContext(this);
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        String temppatientEmail = "";
        if (extras != null) {
            email = extras.getString("patient email");

        } else {
            email = preferences.getString("email", "Email Here");
        }

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

                    float[] statisticValues = new float[(int) dataSnapshot.child("taskList").getChildrenCount()];
                    int i = 0;
                    for (DataSnapshot stat : dataSnapshot.child("taskList").getChildren()) {
                        statisticValues[i] = Integer.parseInt(stat.child("statistic").getValue()+"");
                        i++;
                    }
                    int day = Integer.parseInt(dataSnapshot.child("day").getValue() + "");
                    int month = Integer.parseInt(dataSnapshot.child("month").getValue() + "");
                    int year = Integer.parseInt(dataSnapshot.child("year").getValue() + "");
                    int hour = Integer.parseInt(dataSnapshot.child("hour").getValue() + "");
                    int mins = Integer.parseInt(dataSnapshot.child("mins").getValue() + "");
                    String date = month + "/" + day + "/" + year + "/" + hour + "/" + mins;

                    ArrayList<TaskIndividual> tasks = new ArrayList<TaskIndividual>();

                    for (int j = 0; j < statisticValues.length; j++) {
                        TaskIndividual taskIndividual = new TaskIndividual(taskName, date, (int) statisticValues[j]);
                        tasks.add(taskIndividual);
                    }
                    values = new float[tasks.size()];

                    for (int j = 0; j < tasks.size(); j++) {
                        values[j] = tasks.get(j).getStatistic();
                    }

                    RelativeLayout bar = (RelativeLayout) findViewById(R.id.bar);
                    BarChart our_chart = new BarChart(context, values);
                    bar.addView(our_chart);
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