package com.example.medcheck;

/**
 * Created by Erica on 2/6/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.app.Activity;
import android.widget.RelativeLayout;

public class BarGraph extends Activity {
    float values[]={1, 2, 3, 2, 12, 5};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        RelativeLayout bar =(RelativeLayout) findViewById(R.id.bar);
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

            int step = (255 - 218)/values.length;

            textPaintBox = new Paint[values.length];
            for(int i = 0; i < values.length; i++)
            {
                textPaintBox[i] = new Paint();
            }
            boxPaint = new Paint[values.length];
            for(int i = 0; i < values.length; i++) {
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

            int step = (255 - 218)/boxPaint.length;

            for(int i=0;i<boxPaint.length;i++)
            {

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
            int fullHeight = getHeight();
            int padding = (int) (10 * scaleFactor);
            int maxBarHeight = fullHeight - 10 * padding;
            float bar_height [] = new float[boxPaint.length];
            float max_height = 0;

            for(int i = 0; i < boxPaint.length; i++)
            {
                if (values[i] > max_height)
                {
                    max_height = values[i];
                }
            }

            for(int i = 0; i < boxPaint.length; i++)
            {

                bar_height[i] = (float) ((values[i]/max_height)*maxBarHeight);
            }

            canvas.drawLine(padding, fullHeight - 25 * scaleFactor, fullWidth - padding, fullHeight - 25 * scaleFactor, linePaint);


            for(int i = 0; i < boxPaint.length; i++)
            {
                float barbottom = fullHeight - padding * 3;
                float bartop = barbottom - bar_height[i];
                float valpos = fullHeight;
                canvas.drawRect((1f / boxPaint.length) * i * fullWidth + padding, bartop, (1f / boxPaint.length) * (i + 1) * fullWidth - padding, barbottom, boxPaint[i]);
                canvas.drawText("Week " + (i + 1), (1f/boxPaint.length)*i*fullWidth + 25f/boxPaint.length + padding, valpos, textPaintBox[i]);
                canvas.drawText("Week " + (i + 1), (1f/boxPaint.length)*i*fullWidth + 25f/boxPaint.length + padding, valpos, textPaintBox[i]);
            }

        }

    }
}