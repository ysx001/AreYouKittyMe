package com.example.android.areyoukittyme;


import com.example.android.areyoukittyme.User.User;
import com.example.android.areyoukittyme.plot.DayAxisValueFormatter;
import com.example.android.areyoukittyme.plot.MyAxisValueFormatter;
import com.example.android.areyoukittyme.plot.XYMarkerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
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
import com.github.mikephil.charting.utils.MPPointF;

/**
 *
 */
public class StatsActivity extends AppCompatActivity implements OnSeekBarChangeListener,
        OnChartValueSelectedListener{

    private Button dayButton;

    private Context context;

    private final int day = 1;
    // 52 weeks in a year
    private final int week = 52;
    // 12 month in a year
    private final int month = 12;

    public final int[] STEP_COLORS = { R.color.colorAccent };
    public final int[] FOCUS_COLORS = { R.color.colorAccentLight};
    public final int[] VOCAB_COLORS = { R.color.colorAccentDark};

    private static User mUser = new User("Sarah");


    private ArrayList<ArrayList<Double>> dataArray = User.getUserData();


    protected LineChart monthChart;
    protected BarChart weekChart;


    private SeekBar mSeekBarMonth, mSeekBarWeek;
    private TextView tvMonth, tvWeek;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


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

                startActivity(startStatsIntent);

            }
        });

//        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        tvMonth = (TextView) findViewById(R.id.tvMonthMax);
        tvWeek = (TextView) findViewById(R.id.tvWeekMax);

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

        // set an alternative background color
        monthChart.setBackgroundColor(Color.LTGRAY);

        // add data
        setLineData(this.dataArray, 0);

        monthChart.animateX(2000);

        // get the legend (only possible after setting data)
        Legend lMonth = monthChart.getLegend();

        // modify the legend ...
        lMonth.setForm(LegendForm.LINE);
//        l.setTypeface(mTfLight);
        lMonth.setTextSize(11f);
        lMonth.setTextColor(Color.WHITE);
        lMonth.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lMonth.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        lMonth.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lMonth.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xMonthAxis = monthChart.getXAxis();
//        xMonthAxis.setTypeface(mTfLight);
        xMonthAxis.setTextSize(11f);
        xMonthAxis.setTextColor(Color.WHITE);
        xMonthAxis.setDrawGridLines(false);
        xMonthAxis.setDrawAxisLine(false);

        YAxis leftMonthAxis = monthChart.getAxisLeft();
//        leftMonthAxis.setTypeface(mTfLight);
        leftMonthAxis.setTextColor(ColorTemplate.getHoloBlue());
//        leftMonthAxis.setAxisMaximum(200f);
        //leftMonthAxis.setAxisMinimum(0f);
        leftMonthAxis.setDrawGridLines(true);
        leftMonthAxis.setGranularityEnabled(true);

        YAxis rightMonthAxis = monthChart.getAxisRight();
//        rightMonthAxis.setTypeface(mTfLight);
        rightMonthAxis.setTextColor(Color.RED);
//        rightMonthAxis.setAxisMaximum(900);
        //rightMonthAxis.setAxisMinimum(0);
        rightMonthAxis.setDrawGridLines(false);
        rightMonthAxis.setDrawZeroLine(false);
        rightMonthAxis.setGranularityEnabled(false);
        
        


        weekChart = (BarChart) findViewById(R.id.weekChart);
//        weekChart.setOnChartValueSelectedListener(this);

        weekChart.setDrawBarShadow(false);
        weekChart.setDrawValueAboveBar(true);

//        weekChart.setHighlightPerTapEnabled(true);



        weekChart.getDescription().setEnabled(false);

        // if more than 30 entries are displayed in the chart, no values will be
        // drawn
        weekChart.setMaxVisibleValueCount(30);

        // scaling can now only be done on x- and y-axis separately
        weekChart.setPinchZoom(false);

        weekChart.setDrawGridBackground(false);
        weekChart.animateX(2000);
        // weekChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(weekChart);

        XAxis xWeekAxis = weekChart.getXAxis();
        xWeekAxis.setPosition(XAxisPosition.BOTTOM);
        //xWeekAxis.setTypeface(mTfLight);
        xWeekAxis.setDrawGridLines(false);
        xWeekAxis.setGranularity(1f); // only intervals of 1 day
        xWeekAxis.setLabelCount(7);
        xWeekAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter weekAxisFormat = new MyAxisValueFormatter();

        // Set Left Axis for step data
        YAxis leftWeekAxis = weekChart.getAxisLeft();
        //leftWeekAxis.setTypeface(mTfLight);


        leftWeekAxis.setLabelCount(8, true);
        leftWeekAxis.setValueFormatter(weekAxisFormat);
        leftWeekAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftWeekAxis.setSpaceTop(15f);
        leftWeekAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        // Set Right Axis for focus time
        YAxis rightWeekAxis = weekChart.getAxisRight();

        rightWeekAxis.setDrawGridLines(true);
        //rightWeekAxis.setTypeface(mTfLight);
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
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

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

        // enable back button to main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


//    private ArrayList<ArrayList<Double>> generateData(int count, Double range) {
//
//        ArrayList<ArrayList<Double>> data = new ArrayList<>();
//        ArrayList<Double> stepCounts = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            Double mult = range ;
//            Double val = (Math.random() * mult) + 50;
//            stepCounts.add(val);
//        }
//
//        ArrayList<Double> focusTime = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            Double mult = range / 2.0;
//            Double val = (Math.random() * mult) + 60;
//            focusTime.add(val);
////            if(i == 10) {
////                yVals2.add(new Entry(i, val + 50));
////            }
//        }
//
//        ArrayList<Double> vocabTime = new ArrayList<>();
//
//        for (int i = 0; i < count; i++) {
//            Double mult = range / 5.0;
//            Double val = (Math.random() * mult) + 100;
//            vocabTime.add(val);
//        }
//
//        data.add(stepCounts);
//        data.add(focusTime);
//        data.add(vocabTime);
//
//        return data;
//    }

    private void setLineData(ArrayList<ArrayList<Double>> dataArray, int start) {


        ArrayList<Entry> stepCounts = new ArrayList<>();
        ArrayList<Entry> focusTime = new ArrayList<>();
        ArrayList<Entry> vocabTime = new ArrayList<>();

        for (int i = start ; i < start + 30; i++) {
            stepCounts.add(new Entry(i - start, dataArray.get(0).get(i).floatValue()));
        }

        for (int i = start; i < start + 30; i++) {
            focusTime.add(new Entry(i - start, dataArray.get(1).get(i).floatValue()));
        }

        for (int i = start; i < start + 30; i++) {
            vocabTime.add(new Entry(i - start, dataArray.get(2).get(i).floatValue()));
        }


        LineDataSet stepSet, focusSet, vocabSet;

        if (monthChart.getData() != null &&
                monthChart.getData().getDataSetCount() > 0) {
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
            stepSet.setCircleColor(Color.WHITE);
            stepSet.setLineWidth(2f);
            stepSet.setCircleRadius(3f);
            stepSet.setFillAlpha(65);
            stepSet.setFillColor(Color.BLUE);
            stepSet.setHighLightColor(Color.BLUE);
            stepSet.setDrawCircleHole(false);
            stepSet.setDrawValues(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            focusSet = new LineDataSet(focusTime, "Focus Time");
            focusSet.setAxisDependency(AxisDependency.RIGHT);
            focusSet.setColor(Color.rgb(241,195,208));
            focusSet.setCircleColor(Color.WHITE);
            focusSet.setLineWidth(2f);
            focusSet.setCircleRadius(3f);
            focusSet.setFillAlpha(65);
            focusSet.setFillColor(Color.RED);
            focusSet.setDrawCircleHole(false);
            focusSet.setHighLightColor(Color.rgb(244, 117, 117));
            focusSet.setDrawValues(false);
            //set2.setFillFormatter(new MyFillFormatter(900f));

            vocabSet = new LineDataSet(vocabTime, "Vocab Time");
            vocabSet.setAxisDependency(AxisDependency.RIGHT);
            vocabSet.setColor(Color.rgb(201, 147, 212));
            vocabSet.setCircleColor(Color.WHITE);
            vocabSet.setLineWidth(2f);
            vocabSet.setCircleRadius(3f);
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




    private void setBarData(ArrayList<ArrayList<Double>> dataArray, int start) {

        //float start = 1f;



        ArrayList<BarEntry> stepCounts = new ArrayList<>();
        ArrayList<BarEntry> focusTime = new ArrayList<>();
        ArrayList<BarEntry> vocabTime = new ArrayList<>();

        for (int i = start; i < start + 7; i++) {
            float stepVal = dataArray.get(0).get(i).floatValue();
            float focusVal = dataArray.get(1).get(i).floatValue();
            float vocabVal = dataArray.get(2).get(i).floatValue();

            stepCounts.add(new BarEntry(i - start, new float[] {stepVal, focusVal, vocabVal}));
        }
//
//        for (int i = 0; i < dataArray.get(1).size(); i++) {
//            focusTime.add(new BarEntry(i, dataArray.get(1).get(i).floatValue()));
//        }
//
//        for (int i = 0; i < dataArray.get(2).size(); i++) {
//            vocabTime.add(new BarEntry(i, dataArray.get(2).get(i).floatValue()));
//        }



        BarDataSet step_set;
//        BarDataSet focus_set;
//        BarDataSet vocab_set;

        if (weekChart.getData() != null &&
                weekChart.getData().getDataSetCount() > 0) {

            step_set = (BarDataSet) weekChart.getData().getDataSetByIndex(0);
            step_set.setValues(stepCounts);

//            focus_set = (BarDataSet) weekChart.getData().getDataSetByIndex(1);
//            focus_set.setValues(focusTime);

            weekChart.getData().notifyDataChanged();
            weekChart.notifyDataSetChanged();
        } else {
            step_set = new BarDataSet(stepCounts, "Weekly Stats");
//            focus_set = new BarDataSet(focusTime, "Focus Time");
//            vocab_set = new BarDataSet(vocabTime, "Vocab Time");
//

            step_set.setDrawIcons(false);
            step_set.setColors(getColors(), StatsActivity.this);
            step_set.setStackLabels(new String[]{"Step Count", "Focus Time", "Vocab Time"});
            //step_set.setAxisDependency(AxisDependency.LEFT);

//            focus_set.setDrawIcons(false);
//            focus_set.setColors(FOCUS_COLORS, StatsActivity.this);
//
//            vocab_set.setDrawIcons(false);
//            vocab_set.setColors(VOCAB_COLORS, StatsActivity.this);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();

            dataSets.add(step_set);
//            dataSets.add(focus_set);
//            dataSets.add(vocab_set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            weekChart.setData(data);
        }
        weekChart.setFitBars(true);
        //weekChart.invalidate();
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

    @Override
    public void onBackPressed() {
        Class destActivity = MainActivity.class;
        Context context = StatsActivity.this;

        Intent intent = new Intent(context, destActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvMonth.setText("" + (mSeekBarMonth.getProgress()));
        tvWeek.setText("" + (mSeekBarWeek.getProgress()));

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

    protected RectF mOnValueSelectedRectF = new RectF();

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
}

