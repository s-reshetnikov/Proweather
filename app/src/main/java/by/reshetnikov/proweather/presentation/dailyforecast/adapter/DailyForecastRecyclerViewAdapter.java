package by.reshetnikov.proweather.presentation.dailyforecast.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import by.reshetnikov.proweather.presentation.dailyforecast.DailyForecastViewModel;
import by.reshetnikov.proweather.presentation.dailyforecast.viewholder.DailyForecastViewHolder;

/**
 * Created by s-reshetnikov.
 */

public class DailyForecastRecyclerViewAdapter extends RecyclerView.Adapter<DailyForecastViewHolder> {

    private List<DailyForecastViewModel> forecasts = new ArrayList<>();

    public DailyForecastRecyclerViewAdapter() {
    }

    @Override
    public DailyForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DailyForecastViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(DailyForecastViewHolder holder, int position) {
        Log.i("ForecastAdapt", "bind view holder");
        DailyForecastViewModel forecast = forecasts.get(position);
        holder.setDate(forecast.getDate());
        holder.setMainImage(forecast.getDailyIconCode());
        holder.setTemperature(forecast.getTemperature());
        holder.setHumidity(forecast.getHumidity());
        holder.setWindDetails(forecast.getWindSpeed(), forecast.getWindDirection());
        holder.setPressure(forecast.getPressure());
        holder.setMorningTemperature(forecast.getMorningTemperature());
        holder.setDayTemperature(forecast.getDayTemperature());
        holder.setEveningTemperature(forecast.getEveningTemperature());
        holder.setNightTemperature(forecast.getNightTemperature());
    }

    @NonNull
    public void updateForecast(List<DailyForecastViewModel> forecasts) {
        if (forecasts.size() > 0) {
            this.forecasts = forecasts;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }
}
