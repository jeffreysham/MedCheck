package com.example.medcheck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Erica on 2/6/2016.
 */
public class PieChart extends Activity {
    /** Called when the activity is first created. */
    float values[];
    Context context = this;

    public void goStats(View view) {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    public void drawBar(View view) {
        Intent intent = new Intent(this, BarGraph.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        final String email = preferences.getString("email", "Email Here");
        final ArrayList<TaskIndividual> tasks = new ArrayList<>();

        final Firebase ref = new Firebase("https://medcheck.firebaseio.com/tasks");



        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String patientEmail = (String) dataSnapshot.child("patientEmail").getValue();
                if (patientEmail.equals(email)) {
                    //int statistic = Integer.parseInt(dataSnapshot.child("taskList").child("0").child("statistic").getValue() + "");
                    values = new float[(int) dataSnapshot.getChildrenCount()];
                    Log.i("testing", dataSnapshot.toString());
                    int i = 0;
                    for (DataSnapshot stat : dataSnapshot.child("taskList").getChildren()) {

                        values[i] = Integer.parseInt(stat.child("statistic").getValue()+"");
                        i++;
                    }
                    RelativeLayout pie = (RelativeLayout) findViewById(R.id.pie);
                    pie.addView(new MyGraphview(context, values));
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
    private float[] calculateData(float[] data) {
        // TODO Auto-generated method stub
        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360* (data[i]/total);
        }
        return data;

    }
    public class MyGraphview extends View
    {
        private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] value_degree;
        private int[] COLORS;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float scaleFactor = metrics.density;
        int temp=0;
        public MyGraphview(Context context, float[] raw_values) {

            super(context);
            int r = 85;
            int g = 198;
            int b = 218;

            int value_no = 0;
            int value_yes = 0;

            for(int i = 0; i < raw_values.length; i++)
            {
                if (raw_values[i] <= 0){
                    value_no++;
                } else if (raw_values[i] >= 1) {
                    value_yes++;
                }
            }

            float[] values = {value_yes, value_no};

            int step = (255 - 218)/values.length;

            value_degree=new float[values.length];
            value_degree = calculateData(values);
            COLORS = new int[values.length];
            for(int i=0;i<values.length;i++)
            {
                COLORS[i] = Color.rgb(r, g, b);
                value_degree[i]=values[i];
                r = r + step;
                g = g + step;
                b = b + step;
            }
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float fullWidth = getWidth();
            float fullHeight = getHeight();
            int padding = (int) (10 * scaleFactor);
            float diameter;
            if (fullWidth < fullHeight){
                diameter = fullWidth;
            } else {
                diameter = fullHeight;
            }
            float ceiling = padding + ((fullHeight - 2*padding)/2) - ((diameter - (padding*2))/2);
            //float wall = padding + ((fullWidth - 2*padding)/2) - ((diameter - (padding*2))/2);
            RectF rectf = new RectF (padding, padding + ceiling, diameter - padding, diameter - padding + ceiling);
            Paint textPaint = new Paint();
            textPaint.setColor(0xFFC0C0C0);
            textPaint.setTextSize(35);

            canvas.drawText("Completed: " + (int) values[0], (float) getWidth() / 2 - 200, ceiling - 50, textPaint);
            canvas.drawText("Missed: " + (int)values[1], (float) getWidth()/2 + 30, ceiling - 50, textPaint);
            canvas.drawText("Percent: " + 100*values[0]/(values[0] + values[1]) + "%", (float) getWidth()/2 - 100, ceiling - 15, textPaint);


            for (int i = 0; i < value_degree.length; i++) {//values2.length; i++) {
                if (i == 0) {
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, 0, value_degree[i], true, paint);
                }
                else
                {
                    temp += (int) value_degree[i - 1];
                    paint.setColor(COLORS[i]);
                    canvas.drawArc(rectf, temp, value_degree[i], true, paint);
                }
            }
        }

    }
}