package by.reshetnikov.proweather.data.model.weather.nowforecast;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * Created by s-reshetnikov.
 */

public class HourlyChartData {

    private List<Entry> temperatureData;
    private char unitSign;
    private List<String> xAxisDescription;

    public List<Entry> getTemperatureData() {
        return temperatureData;
    }

    public void setTemperatureData(List<Entry> temperatureData) {
        this.temperatureData = temperatureData;
    }

    public char getUnitSign() {
        return unitSign;
    }

    public void setUnitSign(char unitSign) {
        this.unitSign = unitSign;
    }

    public List<String> getXAxisDescription() {
        return xAxisDescription;
    }

    public void setXAxisDescription(List<String> xAxisDescription) {
        this.xAxisDescription = xAxisDescription;
    }
}
