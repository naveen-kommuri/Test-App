package com.test.testapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by NKommuri on 8/31/2017.
 */

public class DashboardFragment extends Fragment {

    private LineChart mChart;

    public DashboardFragment() {
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.setViewPortOffsets(0, 0, 0, 0);
        //mChart.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(false);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();

        y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.BLACK);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(10, 100);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();
        return view;
    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(i, val));
        }
        ArrayList<Entry> zVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 20;// + (float)
            // ((mult *
            // 0.1) / 10);
            zVals.add(new Entry(i, val));
        }
        LineDataSet set1, set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals);
            set2.setValues(zVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "Sales");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(ContextCompat.getColor(getActivity(), R.color.colorGraphRed));
            set1.setColor(Color.WHITE);
//            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setFillColor(ContextCompat.getColor(getActivity(), R.color.colorGraphRed));
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);
            // set data
            mChart.setData(data);
            set2 = new LineDataSet(zVals, "Purchases");

            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set2.setCubicIntensity(0.2f);
            set2.setDrawFilled(true);
            set2.setDrawCircles(false);
            set2.setLineWidth(1.8f);
            set2.setCircleRadius(4f);
            set2.setCircleColor(Color.WHITE);
            set2.setHighLightColor(ContextCompat.getColor(getActivity(), R.color.colorGraphGreen));
            set2.setFillColor(ContextCompat.getColor(getActivity(), R.color.colorGraphGreen));
//            set2.setFillColor(ColorTemplate.colorWithAlpha(Color.GREEN, 200));
            set2.setColor(Color.WHITE);
//            set2.setFillColor(Color.RED);
            set2.setFillAlpha(200);
            set2.setDrawHorizontalHighlightIndicator(false);
            set2.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data1 = new LineData(set1, set2);
            data1.setValueTextSize(9f);
            data1.setDrawValues(false);
            // set data
            mChart.setData(data1);
        }
    }


}
