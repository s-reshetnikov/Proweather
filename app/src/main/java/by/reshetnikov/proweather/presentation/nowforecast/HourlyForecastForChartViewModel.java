package by.reshetnikov.proweather.presentation.nowforecast;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

import by.reshetnikov.proweather.data.db.model.HoursForecastEntity;
import by.reshetnikov.proweather.data.model.unit.Units;
import by.reshetnikov.proweather.data.preferences.units.TemperatureUnit;
import by.reshetnikov.proweather.utils.CalendarUtil;
import by.reshetnikov.proweather.utils.UnitUtils;

/**
 * Created by s-reshetnikov.
 */

public class HourlyForecastForChartViewModel {

    List<HoursForecastEntity> entities;
    Units units;


    public HourlyForecastForChartViewModel(List<HoursForecastEntity> entities) {
        this.entities = entities;
    }

    public void applyUnits(Units units) {
        this.units = units;
    }

    public List<Pair<Integer, String>> getTemperature() {
        List<Pair<Integer, String>> temperatureHourPairs = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            int temperature = UnitUtils.getTemperature(entities.get(i).getTemperature(), units.getTemperature());
            String date = CalendarUtil.getTimeInHours(entities.get(i).getDate());
            Pair<Integer, String> tempHourPair = new Pair<>(temperature, date);
            temperatureHourPairs.add(tempHourPair);
        }
        return temperatureHourPairs;
    }

    public List<Pair<Double, String>> getPrecipitations() {
        List<Pair<Double, String>> precipitationHourPairs = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            double precipitation = entities.get(i).getSnow() + entities.get(i).getRain();
            String date = CalendarUtil.getTimeInHours(entities.get(i).getDate());
            Pair<Double, String> precipitationHourPair = new Pair<>(precipitation, date);
            precipitationHourPairs.add(precipitationHourPair);
        }
        return precipitationHourPairs;
    }

    public TemperatureUnit getTemperatureUnit() {
        return units.getTemperature();
    }
}
