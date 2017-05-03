package com.example.android.areyoukittyme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.areyoukittyme.User.User;
import com.example.android.areyoukittyme.User.UserData;
import com.example.android.areyoukittyme.plot.MyMarkerView;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StatsDayActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private Button monthweekButton;
    private TextView stepCountView;
    private TextView vocabCountView;
    private TextView focusTimeView;

    private Context context;

    private User mUser;

    private ArrayList<UserData> dataArray  = new ArrayList<>();

    protected HorizontalBarChart dayChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_day);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();

        mUser = startingIntent.getExtras().getParcelable("User");
        System.out.println("dataArray is empty " +  mUser.getUserData().isEmpty());
        dataArray = mUser.getUserData();

        // Store the context variable
        context = StatsDayActivity.this;

        monthweekButton = (Button) findViewById(R.id.monthWeekButton);
        stepCountView = (TextView) findViewById(R.id.stepCountView);
        vocabCountView = (TextView) findViewById(R.id.vocabCountView);
        focusTimeView = (TextView) findViewById(R.id.focusTimeView);

        int stepSize = dataArray.get(0).getData().size();
        int focusSize = dataArray.get(1).getData().size();
        int vocabSize = dataArray.get(2).getData().size();

        //float stepVal = dataArray.get(0).getData().get(stepSize - 1).floatValue();
        float stepVal = mUser.getSteps();
        float focusVal = dataArray.get(1).getData().get(focusSize - 1).floatValue();
        float vocabVal = dataArray.get(2).getData().get(vocabSize - 1).floatValue();

        String stepStr = String.format("Steps Today: %.1f", stepVal);
        String focusStr = String.format("Focus Time: %.1f", focusVal);
        String vocabStr = String.format("Vocab Time: %.1f", vocabVal);

        stepCountView.setText(stepStr);
        focusTimeView.setText(focusStr);
        vocabCountView.setText(vocabStr);

        // Setting an OnClickLister for the statsButton
        monthweekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = StatsActivity.class;

                // create Intent that will start the activity
                Intent startStatsIntent = new Intent(context, destActivity);
                startStatsIntent.putExtra("User", mUser);
                startActivity(startStatsIntent);

            }
        });

        // Plotting
        dayChart = (HorizontalBarChart) findViewById(R.id.dayChart);
        dayChart.setOnChartValueSelectedListener(this);

        dayChart.setDrawValueAboveBar(true);

        dayChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        dayChart.setMaxVisibleValueCount(60);

        // draw shadows for each bar that show the maximum value
        dayChart.setDrawBarShadow(true);

        dayChart.setDrawGridBackground(false);

        // Set marker....
        MyMarkerView mv = new MyMarkerView(context, R.layout.marker_view);

        dayChart.setMarkerView(mv);

        XAxis xl = dayChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = dayChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        YAxis yr = dayChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)

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
    protected void onStop() {
        super.onStop();

        SharedPreferences mPrefs = getSharedPreferences("userPref", StatsDayActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("user", json);

        String inventoryJson = gson.toJson(mUser.getInventoryListObject());
        prefsEditor.putString("inventory", inventoryJson);

        prefsEditor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                this.onBackPressed();
                break;
            }
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
    public void onBackPressed() {
        Class destActivity = MainActivity.class;
        Context context = StatsDayActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void setBarData(ArrayList<UserData> dataArray) {

        float groupSpace = 0.08f;
        float barSpace = 2f; // x3 DataSet
        float barWidth = 1f; // x3 DataSet
        float spaceForBar = 2f;

        int groupCount = 3;
        int start = 0;
        int end = start + groupCount;

        ArrayList<BarEntry> stepCounts = new ArrayList<>();
        ArrayList<BarEntry> focusTime = new ArrayList<>();
        ArrayList<BarEntry> vocabTime = new ArrayList<>();

        int stepSize = dataArray.get(0).getData().size();
        int focusSize = dataArray.get(1).getData().size();
        int vocabSize = dataArray.get(2).getData().size();

        float stepVal = mUser.getSteps() / (float) mUser.getStepsGoal();
        float focusVal = dataArray.get(1).getData().get(focusSize - 1).floatValue() / (float) mUser.getFocusGoal();
        float vocabVal = dataArray.get(2).getData().get(vocabSize - 1).floatValue() / (float) mUser.getVocabGoal();

        stepCounts.add(new BarEntry(0 * spaceForBar , stepVal * 100));
        focusTime.add(new BarEntry(0 * spaceForBar , focusVal * 100));
        vocabTime.add(new BarEntry(0 * spaceForBar , vocabVal * 100));

        BarDataSet stepSet, focusSet, vocabSet;

        if (dayChart.getData() != null &&
                dayChart.getData().getDataSetCount() > 0) {
            stepSet = (BarDataSet) dayChart.getData().getDataSetByIndex(0);
            stepSet.setValues(stepCounts);

            focusSet = (BarDataSet) dayChart.getData().getDataSetByIndex(1);
            focusSet.setValues(focusTime);

            vocabSet = (BarDataSet) dayChart.getData().getDataSetByIndex(2);
            vocabSet.setValues(vocabTime);


            dayChart.getData().notifyDataChanged();
            dayChart.notifyDataSetChanged();
        } else {
            stepSet = new BarDataSet(stepCounts, "Step Count");
            stepSet.setDrawIcons(false);
            stepSet.setColor(Color.rgb(209, 141, 178));

            focusSet = new BarDataSet(focusTime, "Focus Time");
            focusSet.setDrawIcons(false);
            focusSet.setColor(Color.rgb(241,195,208));

            vocabSet = new BarDataSet(vocabTime, "Vocab Count");
            vocabSet.setDrawIcons(false);
            vocabSet.setColor(Color.rgb(201, 147, 212));

            BarData data = new BarData(stepSet, focusSet, vocabSet);
            data.setValueTextSize(10f);

            dayChart.setData(data);

        }
        dayChart.getBarData().setBarWidth(barWidth);

        dayChart.getXAxis().setAxisMaximum(start + dayChart.getBarData().getGroupWidth(groupSpace, barSpace));
        dayChart.groupBars(start, groupSpace, barSpace);
    }

    protected RectF mOnValueSelectedRectF = new RectF();

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        dayChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = dayChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index", "low: " + dayChart.getLowestVisibleX() + ", high: " + dayChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
