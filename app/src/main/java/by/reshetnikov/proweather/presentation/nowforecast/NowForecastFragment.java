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
import by.reshetnikov.proweather.data.model.weather.nowforecast.HourlyChartData;
import by.reshetnikov.proweather.data.model.weather.nowforecast.NowForecastViewModel;
import by.reshetnikov.proweather.di.component.ActivityComponent;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.presentation.weather.WeatherContract;
import by.reshetnikov.proweather.utils.ToastUtils;
import by.reshetnikov.proweather.utils.WeatherStateIconUtil;
import timber.log.Timber;


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
        Timber.d("onAttach");
        if (context instanceof AppCompatActivity) {
            component = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(context))
                    .applicationComponent(((ProWeatherApp) getActivity().getApplication()).getComponent())
                    .build();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.d("onCreateView");
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
                presenter.swipedToRefresh();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("onViewCreated");
        presenter.setView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d("onStart");
        presenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume");
    }

    @Override
    public void onStop() {
        Timber.d("onStop");
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onDetach() {
        Timber.d("onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.d("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showCurrentWeather(NowForecastViewModel nowForecast) {
        tvWeekDay.setText(nowForecast.getWeekDay(ProWeatherApp.getAppContext()));
        ivWeatherState.setImageDrawable(WeatherStateIconUtil.getIcon(nowForecast.getWeatherIconId()));
        tvLocationName.setText(nowForecast.getLocationName());
        tvTemperature.setText(nowForecast.getTemperature());
        tvHumidity.setText(nowForecast.getHumidity());
        tvWindDirection.setText(nowForecast.getWindDirection());
        tvWind.setText(String.valueOf(nowForecast.getWindSpeed()));
    }

    @Override
    public void updateTemperatureChartData(HourlyChartData chartData) {
        LineData lineData = prepareDataForChart(chartData);
        configureChartXAxis(chartData);
        configureChartYAxis();
        setListenersForChart();
        weatherChart.setData(lineData);
        configureChartOptions();
        weatherChart.invalidate();
    }

    private void configureChartOptions() {
        weatherChart.setNoDataTextColor(R.color.colorPrimary);
        weatherChart.setNoDataText(getString(R.string.unable_to_load_forecast));
        int animationDuration = 2 * 1000;
        weatherChart.animateX(animationDuration);
        weatherChart.setHardwareAccelerationEnabled(true);
        weatherChart.setDrawBorders(false);
        weatherChart.setDrawMarkers(true);
        weatherChart.getDescription().setEnabled(false);
        weatherChart.getLegend().setEnabled(false);
        weatherChart.setScaleEnabled(false);
    }

    private void setListenersForChart() {
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
    }

    @NonNull
    private LineData prepareDataForChart(HourlyChartData chartData) {
        LineDataSet temperatureDataSet = getTemperatureDataSet(chartData.getTemperatureData());
        int textSize = 16;
        temperatureDataSet.setValueTextSize(textSize);
        final LineData lineData = new LineData(temperatureDataSet);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf((int) value) + ' ' + chartData.getUnitSign();
            }
        });
        return lineData;
    }

    private void configureChartYAxis() {
        YAxis yAxisLeft = weatherChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setDrawAxisLine(false);
        IAxisValueFormatter emptyAxisValueFormatter = getEmptyAxisValueFormatter();
        yAxisLeft.setValueFormatter(emptyAxisValueFormatter);
        YAxis yAxisRight = weatherChart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setValueFormatter(emptyAxisValueFormatter);
    }

    private void configureChartXAxis(HourlyChartData chartData) {
        XAxis xAxis = weatherChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(chartData.getXAxisDescription().size(), true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return chartData.getXAxisDescription().get((int) value);
            }
        });
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
        ToastUtils.showTurnInternetOnToast();
    }
}
