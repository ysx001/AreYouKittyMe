package com.example.android.areyoukittyme;

import android.annotation.SuppressLint;
import com.example.android.areyoukittyme.User.User;
import com.example.android.areyoukittyme.User.UserData;
import com.example.android.areyoukittyme.plot.DayAxisValueFormatter;
import com.example.android.areyoukittyme.plot.MyAxisValueFormatter;
import com.example.android.areyoukittyme.plot.XYMarkerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 *
 */
public class StatsActivity extends AppCompatActivity implements OnSeekBarChangeListener,
        OnChartValueSelectedListener{

    private Button dayButton;

    private Context context;

    // 52 weeks in a year
    private final int week = 52;
    // 12 month in a year
    private final int month = 12;

    private final int[] STEP_COLORS = { R.color.colorAccent };
    private final int[] FOCUS_COLORS = { R.color.colorAccentLight};
    private final int[] VOCAB_COLORS = { R.color.colorAccentDark};

    private User mUser;


    private ArrayList<UserData> dataArray;


    protected LineChart monthChart;
    protected BarChart weekChart;


    private SeekBar mSeekBarMonth, mSeekBarWeek;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Use getIntent method to store the Intent that started this Activity
        Intent startingIntent = getIntent();

        mUser = startingIntent.getExtras().getParcelable("User");
        dataArray = mUser.getUserData();

        MediaPlayer mPlayer = MediaPlayer.create(StatsActivity.this, R.raw.stats);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.start();


        dayButton = (Button) findViewById(R.id.dayButton);

        // Store the context variable
        context = StatsActivity.this;


        // Setting an OnClickLister for the statsButton
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // This is the class we want to start and open when button is clicked
                Class destActivity = StatsDayActivity.class;

                // create Intent that will start the activity
                Intent startStatsIntent = new Intent(context, destActivity);
                startStatsIntent.putExtra("User", mUser);
                startActivity(startStatsIntent);

            }
        });

        mSeekBarMonth = (SeekBar) findViewById(R.id.monthSeekBar);
        mSeekBarWeek = (SeekBar) findViewById(R.id.weekSeekBar);

        monthChart = (LineChart) findViewById(R.id.monthChart);
        monthChart.setOnChartValueSelectedListener(this);

        // no description text
        monthChart.getDescription().setEnabled(false);

        // enable touch gestures
        monthChart.setTouchEnabled(true);

        monthChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        monthChart.setDragEnabled(true);
        monthChart.setScaleEnabled(true);
        monthChart.setDrawGridBackground(false);
        monthChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        monthChart.setPinchZoom(true);

        // add data
        setLineData(this.dataArray, 0);

        monthChart.animateX(2000);

        IAxisValueFormatter xmonthAxisFormatter = new DayAxisValueFormatter(monthChart);

        // get the legend (only possible after setting data)
        Legend lMonth = monthChart.getLegend();

        // modify the legend ...
        lMonth.setForm(LegendForm.LINE);
        lMonth.setTextSize(11f);
        lMonth.setTextColor(Color.DKGRAY);
        lMonth.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lMonth.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lMonth.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lMonth.setDrawInside(false);

        XAxis xMonthAxis = monthChart.getXAxis();
        xMonthAxis.setTextSize(11f);
        xMonthAxis.setTextColor(Color.DKGRAY);
        xMonthAxis.setDrawGridLines(false);
        xMonthAxis.setValueFormatter(xmonthAxisFormatter);

        YAxis leftMonthAxis = monthChart.getAxisLeft();
        leftMonthAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftMonthAxis.setDrawGridLines(true);
        leftMonthAxis.setGranularityEnabled(true);

        YAxis rightMonthAxis = monthChart.getAxisRight();
        rightMonthAxis.setTextColor(Color.RED);
        rightMonthAxis.setDrawGridLines(false);
        rightMonthAxis.setDrawZeroLine(false);
        rightMonthAxis.setGranularityEnabled(false);

        weekChart = (BarChart) findViewById(R.id.weekChart);

        weekChart.setDrawBarShadow(false);
        weekChart.setDrawValueAboveBar(true);

        weekChart.getDescription().setEnabled(false);

        // if more than 30 entries are displayed in the chart, no values will be
        // drawn
        weekChart.setMaxVisibleValueCount(30);

        // scaling can now only be done on x- and y-axis separately
        weekChart.setPinchZoom(false);

        weekChart.setDrawGridBackground(false);
        weekChart.animateX(2000);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(weekChart);

        XAxis xWeekAxis = weekChart.getXAxis();
        xWeekAxis.setPosition(XAxisPosition.BOTTOM);
        xWeekAxis.setGranularity(1f); // only intervals of 1 day
        xWeekAxis.setLabelCount(7);
        xWeekAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter weekAxisFormat = new MyAxisValueFormatter();

        // Set Left Axis for step data
        YAxis leftWeekAxis = weekChart.getAxisLeft();

        leftWeekAxis.setLabelCount(8, true);
        leftWeekAxis.setValueFormatter(weekAxisFormat);
        leftWeekAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftWeekAxis.setSpaceTop(15f);
        leftWeekAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        // Set Right Axis for focus time
        YAxis rightWeekAxis = weekChart.getAxisRight();

        rightWeekAxis.setLabelCount(8, false);
        rightWeekAxis.setValueFormatter(weekAxisFormat);
        rightWeekAxis.setSpaceTop(15f);
        rightWeekAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend lweek = weekChart.getLegend();
        lweek.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lweek.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lweek.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lweek.setDrawInside(false);
        lweek.setForm(LegendForm.SQUARE);
        lweek.setFormSize(9f);
        lweek.setTextSize(11f);
        lweek.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(weekChart); // For bounds control
        weekChart.setMarker(mv); // Set the marker to the chart

        setBarData(this.dataArray, 0);

        // setting data
        mSeekBarWeek.setProgress(0);
        mSeekBarMonth.setProgress(0);

        mSeekBarMonth.setMax(month);
        mSeekBarWeek.setMax(week);

        mSeekBarWeek.setOnSeekBarChangeListener(this);
        mSeekBarMonth.setOnSeekBarChangeListener(this);
    }

    /**
     * Sets up the line display with the data given.
     * @param dataArray The data to be displayed.
     * @param start The starting point of the data.
     */
    private void setLineData(ArrayList<UserData> dataArray, int start) {
        ArrayList<Entry> stepCounts = new ArrayList<>();
        ArrayList<Entry> focusTime = new ArrayList<>();
        ArrayList<Entry> vocabTime = new ArrayList<>();

        for (int i = start ; i < start + 30; i++) {
            stepCounts.add(new Entry(i - start, dataArray.get(0).getData().get(i).floatValue()/(float)mUser.getStepsGoal()));
        }

        for (int i = start; i < start + 30; i++) {
            focusTime.add(new Entry(i - start, dataArray.get(1).getData().get(i).floatValue()/(float)mUser.getFocusGoal()));
        }

        for (int i = start; i < start + 30; i++) {
            vocabTime.add(new Entry(i - start, dataArray.get(2).getData().get(i).floatValue()/(float)mUser.getVocabGoal()));
        }

        LineDataSet stepSet, focusSet, vocabSet;

        if (monthChart.getData() != null && monthChart.getData().getDataSetCount() > 0) {
            stepSet = (LineDataSet) monthChart.getData().getDataSetByIndex(0);
            focusSet = (LineDataSet) monthChart.getData().getDataSetByIndex(1);
            vocabSet = (LineDataSet) monthChart.getData().getDataSetByIndex(2);
            stepSet.setValues(stepCounts);
            focusSet.setValues(focusTime);
            vocabSet.setValues(vocabTime);
            monthChart.getData().notifyDataChanged();
            monthChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            stepSet = new LineDataSet(stepCounts, "Step Count");

            stepSet.setAxisDependency(AxisDependency.LEFT);
            stepSet.setColor(Color.rgb(209, 141, 178));
            stepSet.setCircleColor(Color.rgb(209, 198, 191));
            stepSet.setLineWidth(2f);
            stepSet.setCircleRadius(2f);
            stepSet.setFillAlpha(65);
            stepSet.setFillColor(Color.BLUE);
            stepSet.setHighLightColor(Color.BLUE);
            stepSet.setDrawCircleHole(false);
            stepSet.setDrawValues(false);

            // create a dataset and give it a type
            focusSet = new LineDataSet(focusTime, "Focus Time");
            focusSet.setAxisDependency(AxisDependency.RIGHT);
            focusSet.setColor(Color.rgb(241,195,208));
            focusSet.setCircleColor(Color.rgb(209, 198, 191));
            focusSet.setLineWidth(2f);
            focusSet.setCircleRadius(2f);
            focusSet.setFillAlpha(65);
            focusSet.setFillColor(Color.RED);
            focusSet.setDrawCircleHole(false);
            focusSet.setHighLightColor(Color.rgb(244, 117, 117));
            focusSet.setDrawValues(false);

            vocabSet = new LineDataSet(vocabTime, "Vocab Time");
            vocabSet.setAxisDependency(AxisDependency.RIGHT);
            vocabSet.setColor(Color.rgb(201, 147, 212));
            vocabSet.setCircleColor(Color.rgb(209, 198, 191));
            vocabSet.setLineWidth(2f);
            vocabSet.setCircleRadius(2f);
            vocabSet.setFillAlpha(65);
            vocabSet.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            vocabSet.setDrawCircleHole(false);
            vocabSet.setHighLightColor(Color.rgb(244, 117, 117));
            vocabSet.setDrawValues(false);

            // create a data object with the datasets
            LineData data = new LineData(stepSet, focusSet, vocabSet);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            monthChart.setData(data);
        }
    }

    /**
     * Sets up the bar display with the data given
     * @param dataArray The data to be displayed.
     * @param start The starting point of the data.
     */
    private void setBarData(ArrayList<UserData> dataArray, int start) {

        ArrayList<BarEntry> stepCounts = new ArrayList<>();
        ArrayList<BarEntry> focusTime = new ArrayList<>();
        ArrayList<BarEntry> vocabTime = new ArrayList<>();

        for (int i = start; i < start + 7; i++) {
            float stepVal = dataArray.get(0).getData().get(i).floatValue()/(float) mUser.getStepsGoal();
            float focusVal = dataArray.get(1).getData().get(i).floatValue()/(float) mUser.getFocusGoal();
            float vocabVal = dataArray.get(2).getData().get(i).floatValue()/(float) mUser.getVocabGoal();

            stepCounts.add(new BarEntry(i - start, new float[] {stepVal, focusVal, vocabVal}));
        }

        BarDataSet step_set;

        if (weekChart.getData() != null &&
                weekChart.getData().getDataSetCount() > 0) {

            step_set = (BarDataSet) weekChart.getData().getDataSetByIndex(0);
            step_set.setValues(stepCounts);

            weekChart.getData().notifyDataChanged();
            weekChart.notifyDataSetChanged();
        } else {
            step_set = new BarDataSet(stepCounts, "Weekly Stats");

            step_set.setDrawIcons(false);
            step_set.setColors(getColors(), StatsActivity.this);
            step_set.setStackLabels(new String[]{"Step Count", "Focus Time", "Vocab Time"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();

            dataSets.add(step_set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            weekChart.setData(data);
        }
        weekChart.setFitBars(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
            case R.id.actionToggleValues: {
                for (IDataSet set : weekChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                weekChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet set : weekChart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

                weekChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (weekChart.getData() != null) {
                    weekChart.getData().setHighlightEnabled(!weekChart.getData().isHighlightEnabled());
                    weekChart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (weekChart.isPinchZoomEnabled())
                    weekChart.setPinchZoom(false);
                else
                    weekChart.setPinchZoom(true);

                weekChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                weekChart.setAutoScaleMinMaxEnabled(!weekChart.isAutoScaleMinMaxEnabled());
                weekChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : weekChart.getData().getDataSets())
                    ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                weekChart.invalidate();
                break;
            }
            case R.id.animateX: {
                weekChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                weekChart.animateY(100);
                break;
            }
            case R.id.animateXY: {

                weekChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (weekChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
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

    /**
     * On back pressed, it goes to MainActivity
     */
    @Override
    public void onBackPressed() {
        Class destActivity = MainActivity.class;
        Context context = StatsActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * When progress is changed, we update the view
     * @param seekBar
     * @param progress The current progress.
     * @param fromUser
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        setLineData(dataArray , mSeekBarMonth.getProgress());
        monthChart.invalidate();

        setBarData(dataArray , mSeekBarWeek.getProgress());
        weekChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + monthChart.getLowestVisibleX() + ", high: " + monthChart.getHighestVisibleX
                ());
        Log.i("MIN MAX", "xmin: " + monthChart.getXChartMin() + ", xmax: " + monthChart.getXChartMax() + ", ymin: " + monthChart.getYChartMin() + ", ymax: " + monthChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences mPrefs = getSharedPreferences("userPref", StatsActivity.this.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefsEditor.putString("user", json);

        String inventoryJson = gson.toJson(mUser.getInventoryListObject());
        prefsEditor.putString("inventory", inventoryJson);

        prefsEditor.commit();
    }
}
