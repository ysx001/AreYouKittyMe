package com.example.android.areyoukittyme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

public class StatsDayActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private Button monthweekButton;

    private Context context;

    private final int day = 1;
    // 52 weeks in a year
    private final int week = 52;
    // 12 month in a year
    private final int month = 12;
    private final int year = 365;

    public final int[] STEP_COLORS = { R.color.colorAccent };
    public final int[] FOCUS_COLORS = { R.color.colorAccentLight};
    public final int[] VOCAB_COLORS = { R.color.colorAccentDark};


    private ArrayList<ArrayList<Double>> dataArray = generateData(year, 30.0);


    protected HorizontalBarChart dayChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_day);

        // Store the context variable
        context = StatsDayActivity.this;

        monthweekButton = (Button) findViewById(R.id.monthWeekButton);


        // Setting an OnClickLister for the statsButton
        monthweekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = StatsActivity.class;

                // create Intent that will start the activity
                Intent startStatsIntent = new Intent(context, destActivity);

                startActivity(startStatsIntent);

            }
        });

        // Plotting

        dayChart = (HorizontalBarChart) findViewById(R.id.dayChart);
        dayChart.setOnChartValueSelectedListener(this);
        // dayChart.setHighlightEnabled(false);

        dayChart.setDrawBarShadow(false);

        dayChart.setDrawValueAboveBar(true);

        dayChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        dayChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        dayChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
//         dayChart.setDrawBarShadow(true);

        dayChart.setDrawGridBackground(false);

        XAxis xl = dayChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xl.setTypeface(mTfLight);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = dayChart.getAxisLeft();
//        yl.setTypeface(mTfLight);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = dayChart.getAxisRight();
//        yr.setTypeface(mTfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        // add data
        setBarData(this.dataArray);

        dayChart.setFitBars(true);
        dayChart.animateY(2500);


        Legend l = dayChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                List<IBarDataSet> sets = dayChart.getData()
                        .getDataSets();

                for (IBarDataSet iSet : sets) {

                    IBarDataSet set = (BarDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                dayChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                List<IBarDataSet> sets = dayChart.getData()
                        .getDataSets();

                for (IBarDataSet iSet : sets) {

                    IBarDataSet set = (BarDataSet) iSet;
                    set.setDrawIcons(!set.isDrawIconsEnabled());
                }

                dayChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if(dayChart.getData() != null) {
                    dayChart.getData().setHighlightEnabled(!dayChart.getData().isHighlightEnabled());
                    dayChart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (dayChart.isPinchZoomEnabled())
                    dayChart.setPinchZoom(false);
                else
                    dayChart.setPinchZoom(true);

                dayChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                dayChart.setAutoScaleMinMaxEnabled(!dayChart.isAutoScaleMinMaxEnabled());
                dayChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : dayChart.getData().getDataSets())
                    ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);
                dayChart.invalidate();
                break;
            }
            case R.id.animateX: {
                dayChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                dayChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                dayChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (dayChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }


    private ArrayList<ArrayList<Double>> generateData(int count, Double range) {

        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        ArrayList<Double> stepCounts = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range ;
            Double val = (Math.random() * mult) + 50;
            stepCounts.add(val);
        }

        ArrayList<Double> focusTime = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range / 2.0;
            Double val = (Math.random() * mult) + 60;
            focusTime.add(val);
//            if(i == 10) {
//                yVals2.add(new Entry(i, val + 50));
//            }
        }

        ArrayList<Double> vocabTime = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Double mult = range / 5.0;
            Double val = (Math.random() * mult) + 100;
            vocabTime.add(val);
        }

        data.add(stepCounts);
        data.add(focusTime);
        data.add(vocabTime);

        return data;
    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        colors[0] = STEP_COLORS[0];
        colors[1] = FOCUS_COLORS[0];
        colors[2] = VOCAB_COLORS[0];

        return colors;
    }

    private void setBarData(ArrayList<ArrayList<Double>> dataArray) {

        float barWidth = 1f;
        float spaceForBar = 2f;

        ArrayList<BarEntry> stepCounts = new ArrayList<>();
//        ArrayList<BarEntry> focusTime = new ArrayList<>();
//        ArrayList<BarEntry> vocabTime = new ArrayList<>();

        int stepSize = dataArray.get(0).size();
        int focusSize = dataArray.get(1).size();
        int vocabSize = dataArray.get(2).size();

        float stepVal = dataArray.get(0).get(stepSize - 1).floatValue();
        float focusVal = dataArray.get(1).get(focusSize - 1).floatValue();
        float vocabVal = dataArray.get(2).get(vocabSize - 1).floatValue();

        stepCounts.add(new BarEntry(0 * spaceForBar , stepVal));
        stepCounts.add(new BarEntry(1 * spaceForBar , focusVal));
        stepCounts.add(new BarEntry(2 * spaceForBar , vocabVal));

        BarDataSet stepSet, focusSet, vocabSet;

        if (dayChart.getData() != null &&
                dayChart.getData().getDataSetCount() > 0) {
            stepSet = (BarDataSet) dayChart.getData().getDataSetByIndex(0);
            stepSet.setValues(stepCounts);

//
//            focusSet = (BarDataSet) dayChart.getData().getDataSetByIndex(0);
//            focusSet.setValues(focusTime);
//
//            vocabSet = (BarDataSet) dayChart.getData().getDataSetByIndex(0);
//            vocabSet.setValues(vocabTime);

            dayChart.getData().notifyDataChanged();
            dayChart.notifyDataSetChanged();
        } else {
            stepSet = new BarDataSet(stepCounts, "Step Count");
            stepSet.setDrawIcons(false);
//            stepSet.setColor(STEP_COLORS[0]);
            stepSet.setColors(getColors(), StatsDayActivity.this);

//            focusSet = new BarDataSet(focusTime, "Focus Time");
//            focusSet.setDrawIcons(false);
//            focusSet.setColor(FOCUS_COLORS[0]);
//
//            vocabSet = new BarDataSet(vocabTime, "Vocab Time");
//            vocabSet.setDrawIcons(false);
//            vocabSet.setColor(VOCAB_COLORS[0]);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(stepSet);
//            dataSets.add(focusSet);
//            dataSets.add(vocabSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            dayChart.setData(data);
        }
    }



    protected RectF mOnValueSelectedRectF = new RectF();
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        dayChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = dayChart.getPosition(e, dayChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    };


}
