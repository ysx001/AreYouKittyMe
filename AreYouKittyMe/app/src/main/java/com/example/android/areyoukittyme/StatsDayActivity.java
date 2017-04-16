package com.example.android.areyoukittyme;

import android.annotation.SuppressLint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class StatsDayActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    protected HorizontalBarChart dayChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_day);
        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);

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
        // dayChart.setDrawBarShadow(true);

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

        setData(3, 50);
        dayChart.setFitBars(true);
        dayChart.animateY(2500);

        // setting data
        mSeekBarY.setProgress(50);
        mSeekBarX.setProgress(12);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
        dayChart.setFitBars(true);
        dayChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {

        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            yVals1.add(new BarEntry(i * spaceForBar, val,
                    getResources().getDrawable(R.drawable.star)));
        }

        BarDataSet set1;

        if (dayChart.getData() != null &&
                dayChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) dayChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            dayChart.getData().notifyDataChanged();
            dayChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

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
