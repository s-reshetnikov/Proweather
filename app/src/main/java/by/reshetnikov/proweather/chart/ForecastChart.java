package by.reshetnikov.proweather.chart;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by s-reshetnikov.
 */

public class ForecastChart extends LineChart {
    private boolean isValuesDrawn = false;

    public ForecastChart(Context context) {
        super(context);
    }


}
