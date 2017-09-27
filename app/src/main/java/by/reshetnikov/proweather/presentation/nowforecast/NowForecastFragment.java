package by.reshetnikov.proweather.presentation.nowforecast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.di.component.ActivityComponent;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.presentation.weather.WeatherContract;
import by.reshetnikov.proweather.utils.ToastUtils;
import by.reshetnikov.proweather.utils.WeatherStateIconUtil;


public class NowForecastFragment extends Fragment implements NowForecastContract.View {
    private static final String ARG_SECTION_NUMBER = "SECTION_NUMBER";

    @BindView(R.id.swipeNowForecast)
    SwipeRefreshLayout refreshLayout;
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
    @BindView(R.id.ivStateWindDirection)
    ImageView ivStatePressure;
    @BindView(R.id.ivStateWindSpeed)
    ImageView ivStateWind;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.tvWindDirectionDataValue)
    TextView tvWindDirection;
    @BindView(R.id.tvWindSpeedDataValue)
    TextView tvWind;

    @Inject
    NowForecastPresenter presenter;

    private ActivityComponent component;
    private OnFragmentInteractionListener mListener;


    public NowForecastFragment() {
    }

    public static NowForecastFragment newInstance(int sectionNumber) {
        NowForecastFragment fragment = new NowForecastFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_forecast, container, false);
        ButterKnife.bind(this, view);
        component.inject(this);
        setupSwipeToRefresh();
        return view;
    }

    private void setupSwipeToRefresh() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshClicked();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onFragmentPressed(String message) {
        if (mListener != null) {
            mListener.onCurrentWeatherFragmentInteraction(message);
        }
    }


    @Override
    public void showCurrentWeather(NowForecastViewModel nowForecast) {
        tvWeekDay.setText(nowForecast.getWeekDay());
        ivWeatherState.setImageDrawable(WeatherStateIconUtil.getIcon(nowForecast.getWeatherIconId()));
        tvLocationName.setText(nowForecast.getLocationName());
        tvTemperature.setText(nowForecast.getTemperature());
        tvHumidity.setText(nowForecast.getHumidity());
        tvWindDirection.setText(nowForecast.getWindDirection());
        tvWind.setText(String.valueOf(nowForecast.getWindSpeed()));
    }

    @Override
    public void updateTemperatureChartData(List<Entry> temperatureForecast, final char unitSign, final List<String> xAxisDescription) {
        LineDataSet temperatureDataSet = getTemperatureDataSet(temperatureForecast);
        final LineData lineData = new LineData(temperatureDataSet);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int) value) + ' ' + unitSign;
            }
        });
        weatherChart.setData(lineData);
        XAxis xAxis = weatherChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(xAxisDescription.size(), true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisDescription.get((int) value);
            }
        });
        YAxis yAxisLeft = weatherChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
        IAxisValueFormatter emptyAxisValueFormatter = getEmptyAxisValueFormatter();
        yAxisLeft.setValueFormatter(emptyAxisValueFormatter);
        YAxis yAxisRight = weatherChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setValueFormatter(emptyAxisValueFormatter);
        weatherChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof LineChart) {
                    for (ILineDataSet lineDataSet : ((LineChart) v).getLineData().getDataSets())
                        lineDataSet.setDrawValues(!lineDataSet.isDrawValuesEnabled());
                    v.invalidate();
                }
            }
        });
        int animationDuration = 2 * 1000;
        weatherChart.animateX(animationDuration);
        weatherChart.setHardwareAccelerationEnabled(true);
        weatherChart.setDrawBorders(false);
        weatherChart.setDrawMarkers(true);
        weatherChart.getDescription().setEnabled(false);
        weatherChart.getLegend().setEnabled(false);
        weatherChart.setScaleEnabled(false);
        weatherChart.setNoDataText("No forecast was loaded");
        weatherChart.invalidate();
    }

    @NonNull
    private LineDataSet getTemperatureDataSet(List<Entry> forecast) {
        LineDataSet temperatureDataSet = new LineDataSet(forecast, "");
        temperatureDataSet.setDrawCircles(false);
        temperatureDataSet.setDrawCircleHole(false);
        temperatureDataSet.setDrawFilled(true);
        temperatureDataSet.disableDashedLine();
        temperatureDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        float lineWidthDp = 3;
        temperatureDataSet.setLineWidth(lineWidthDp);
        // values
        temperatureDataSet.setDrawValues(false);
        temperatureDataSet.setFillColor(ContextCompat.getColor(getContext(), R.color.colorAccentLight));
        temperatureDataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        temperatureDataSet.setHighlightEnabled(false);
        return temperatureDataSet;
    }

    @NonNull
    private IAxisValueFormatter getEmptyAxisValueFormatter() {
        return new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        };
    }

    @Override
    public void showLocationManager() {
        WeatherContract.View parentView = (WeatherContract.View) getActivity();
        parentView.startLocationActivity();
    }

    @Override
    public void showTurnInternetOn() {
        Toast toast = Toast.makeText(this.getContext(), "Turn on internet to get weather forecast", Toast.LENGTH_LONG);
        ToastUtils.showToast(toast);
    }

    @Override
    public void showMessage(String message) {
        Toast toast = Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG);
        ToastUtils.showToast(toast);
    }

    @Override
    public void onError(String message) {
        Toast toast = Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG);
        ToastUtils.showToast(toast);
    }

    public interface OnFragmentInteractionListener {
        void onCurrentWeatherFragmentInteraction(String message);
    }
}
