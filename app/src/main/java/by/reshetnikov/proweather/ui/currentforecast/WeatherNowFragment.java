package by.reshetnikov.proweather.ui.currentforecast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.model.weather.current.CurrentForecastAdapterContract;
import by.reshetnikov.proweather.di.component.ActivityComponent;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.utils.CalendarUtil;
import by.reshetnikov.proweather.utils.WeatherStateIconUtil;


public class WeatherNowFragment extends Fragment implements CurrentForecastContract.View {
    private static final String ARG_SECTION_NUMBER = "SECTION_NUMBER";

    @BindView(R.id.tvWeekDay)
    TextView tvWeekDay;
    @BindView(R.id.ivWeatherState)
    ImageView ivWeatherState;
    @BindView(R.id.tvTemperature)
    TextView tvTemperature;
    @BindView(R.id.tvLocationName)
    TextView tvLocationName;
    @BindView(R.id.weatherChart)
    LineChart weatherChart;
    @BindView(R.id.ivStateRainy)
    ImageView ivStateRainy;
    @BindView(R.id.ivStatePrecipitation)
    ImageView ivStatePrecipitation;
    @BindView(R.id.ivStateWind)
    ImageView ivStateWind;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.tvPrecipitation)
    TextView tvPrecipitation;
    @BindView(R.id.tvWind)
    TextView tvWind;

    @Inject
    CurrentForecastPresenter presenter;
    @Inject
    WeatherStateIconUtil iconUtil;

    private ActivityComponent component;
    private OnFragmentInteractionListener mListener;


    public WeatherNowFragment() {
    }

    public static WeatherNowFragment newInstance(int sectionNumber) {
        WeatherNowFragment fragment = new WeatherNowFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_forecast, container, false);
        ButterKnife.bind(this, view);
        component.inject(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.loadCurrentWeather();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onFragmentPressed(String message) {
        if (mListener != null) {
            mListener.onCurrentWeatherFragmentInteraction(message);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            component = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule((AppCompatActivity) context))
                    .applicationComponent(((ProWeatherApp) getActivity().getApplication()).getComponent())
                    .build();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        presenter.start();
    }

    @Override
    public void showCurrentWeather(CurrentForecastAdapterContract currentWeather) {
        tvWeekDay.setText(CalendarUtil.getWeekDay());
        ivWeatherState.setImageDrawable(iconUtil.getIcon(currentWeather.getWeatherConditionId()));
        tvTemperature.setText(String.valueOf(currentWeather.getTemperature()));
        tvHumidity.setText(currentWeather.getHumidity());
        tvPrecipitation.setText(String.valueOf(currentWeather.getPrecipitation()));
        tvWind.setText(String.valueOf(currentWeather.getWindSpeed()));
    }

    @Override
    public void updateTimeLineData(List<Entry> forecast) {
        LineDataSet dataSet = new LineDataSet(forecast, "Label");
//        dataSet.setColor(...);
//        dataSet.setValueTextColor(...);
        LineData lineData = new LineData(dataSet);
        weatherChart.setData(lineData);
        weatherChart.invalidate();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onError(String message) {

    }

    public interface OnFragmentInteractionListener {
        void onCurrentWeatherFragmentInteraction(String message);
    }
}
