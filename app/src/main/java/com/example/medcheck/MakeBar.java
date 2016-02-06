package com.example.medcheck;

/**
 * Created by Erica on 2/6/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.app.Activity;
import android.widget.RelativeLayout;

public class MakeBar extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout relative=(RelativeLayout) findViewById(R.id.relative);
        BarChart our_chart = new BarChart(this);
        our_chart.setCost(50);
        our_chart.setEarnings(50);
        relative.addView(our_chart);

    }

    public class BarChart extends View {
        Paint textPaint;
        Paint linePaint;
        Paint boxPaint1;
        Paint boxPaint2;
        Paint textPaint1;
        Paint textPaint2;
        double cost;
        double earnings;
        float scaleFactor;

        public BarChart(Context context) {
            super(context);
            initialise();
        }

        public BarChart(Context context, AttributeSet attrs) {
            super(context, attrs);
            initialise();
        }

        public BarChart(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initialise();
        }

        void initialise() {

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            scaleFactor = metrics.density;

            textPaint = new Paint();
            linePaint = new Paint();
            boxPaint1 = new Paint();
            boxPaint2 = new Paint();
            textPaint1 = new Paint();
            textPaint2 = new Paint();
            linePaint.setStrokeWidth(1);
            linePaint.setColor(0xFFC5C5C5);
            textPaint.setColor(0xFFC5C5C5);
            textPaint.setTextSize(14 * scaleFactor);
            boxPaint1.setColor(0xFF55C6DA);
            boxPaint2.setColor(0xFF2D9EB2);
            textPaint1.setColor(0xFF55C6DA);
            textPaint2.setColor(0xFF2D9EB2);
            textPaint1.setTextSize(14 * scaleFactor);
            textPaint2.setTextSize(14 * scaleFactor);

        }

        // Earnings and cost are calculated values based on user inputs.
        // The Main app Activity calculates these and calls the below methods
        // when the user inputs a new value

        public void setCost(double value) {
            cost = value;
            invalidate();
        }

        public void setEarnings(double value) {
            earnings = value;
            invalidate();
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int fullWidth = getWidth();
            int fullHeight = getHeight();
            int padding = (int) (10 * scaleFactor);
            int maxBarHeight = fullHeight - 5 * padding;
            float bar1height;
            float bar2height;

            if (earnings > cost) {
                bar2height = (float) maxBarHeight;
                bar1height = (float) (cost / earnings * maxBarHeight);
            } else {
                bar1height = (float) maxBarHeight;
                bar2height = (float) (earnings / cost * maxBarHeight);
            }

            canvas.drawLine(padding, fullHeight - 25 * scaleFactor, fullWidth - padding, fullHeight - 25 * scaleFactor, linePaint);

            float middle = (float) (fullWidth * 0.5);
            float quarter = (float) (fullWidth * 0.25);
            float threequarter = (float) (fullWidth * 0.75);

            int bar1bottom = fullHeight - padding * 3;
            float bar1top = bar1bottom - bar1height;
            float val1pos = bar1top - padding;
            canvas.drawRect(padding * 2, bar1top, middle - padding, bar1bottom, boxPaint1);
            canvas.drawText("This Week", quarter - padding, fullHeight - padding, textPaint1);
            canvas.drawText("" + cost + "%", quarter - padding, val1pos, textPaint);

            int bar2bottom = fullHeight - padding * 3;
            float bar2top = bar2bottom - bar2height;
            float val2pos = bar2top - padding;
            canvas.drawRect(middle + padding, bar2top, fullWidth - padding * 2, bar2bottom, boxPaint2);
            canvas.drawText("Last Week", threequarter - padding * 3, fullHeight - padding, textPaint2);
            canvas.drawText("" + earnings + "%", threequarter - padding * 2, val2pos, textPaint);
        }

    }
}