package by.reshetnikov.proweather.currentweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.appmodels.CurrentWeatherAppModel;


public class CurrentWeatherFragment extends Fragment implements CurrentWeatherContract.View {

    CurrentWeatherPresenter presenter;

    @BindView(R.id.tv_feels_like_temp)
    TextView tvFeelsLikeTemperature;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_max_min_temp)
    TextView tvMaxMinTemperature;
    @BindView(R.id.tv_precipitation)
    TextView tvPrecipitation;
    @BindView(R.id.tv_temp)
    TextView tvTemperature;
    @BindView(R.id.tv_wind)
    TextView tvWind;
    @BindView(R.id.btn_weather_details)
    Button btnWeatherDetails;
    @BindView(R.id.img_weather)
    ImageView imgWeather;
    private OnFragmentInteractionListener mListener;

    public CurrentWeatherFragment() {
    }

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        //ProWeatherApp.getProWeatherApp().getAppComponent().inject(this);
        presenter = new CurrentWeatherPresenter();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.initialize();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        presenter.onDestroy();
    }

    @OnClick(R.id.btn_weather_details)
    public void onClick(View v) {
        onFragmentPressed("Button clicked");
    }

    @Override
    public void showCurrentWeather(CurrentWeatherAppModel currentWeather) {
        if (currentWeather != null) {
            tvHumidity.setText(currentWeather.getHumidityText());
            tvTemperature.setText(currentWeather.getTemperatureText());
            tvWind.setText(currentWeather.getWindSpeedText() + ", " + currentWeather.getWindDirectionText());
        }
    }

    public interface OnFragmentInteractionListener {
        void onCurrentWeatherFragmentInteraction(String message);
    }
}
