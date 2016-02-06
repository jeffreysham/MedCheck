package com.example.medcheck;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Erica on 2/6/2016.
 */
public class PieChart extends Activity {
    /** Called when the activity is first created. */
    float values[]={1, 2};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout relative=(RelativeLayout) findViewById(R.id.relative);
        values=calculateData(values);
        relative.addView(new MyGraphview(this,values));

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
            data[i]=360*(data[i]/total);
        }
        return data;

    }
    public class MyGraphview extends View
    {
        private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        private float[] value_degree;
        private int[] COLORS;
        RectF rectf = new RectF (50, 50, 500, 500);
        int temp=0;
        public MyGraphview(Context context, float[] values) {

            super(context);
            int r = 85;
            int g = 198;
            int b = 218;

            int step = (255 - 218)/values.length;

            value_degree=new float[values.length];
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
            // TODO Auto-generated method stub
            super.onDraw(canvas);

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