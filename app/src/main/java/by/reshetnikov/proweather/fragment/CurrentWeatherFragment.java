package by.reshetnikov.proweather.fragment;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.contract.CurrentWeatherContract;
import by.reshetnikov.proweather.data.model.CurrentWeatherAdapterModel;
import by.reshetnikov.proweather.injector.component.ActivityComponent;
import by.reshetnikov.proweather.injector.component.DaggerActivityComponent;
import by.reshetnikov.proweather.injector.module.ActivityModule;
import by.reshetnikov.proweather.presenter.CurrentWeatherPresenter;


public class CurrentWeatherFragment extends Fragment implements CurrentWeatherContract.View {

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
    @Inject
    CurrentWeatherPresenter presenter;
    @BindView(R.id.img_weather)
    ImageView imgWeather;
    private ActivityComponent component;
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
        ButterKnife.bind(this, view);
        component.inject(this);
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
        if (context instanceof AppCompatActivity) {
            component = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule((AppCompatActivity) context))
                    .applicationComponent(((ProWeatherApp) getActivity().getApplication()).getComponent())
                    .build();
        }
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
        presenter.start();
    }

    @Override
    public void showCurrentWeather(CurrentWeatherAdapterModel currentWeather) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onError(String message) {

    }

//    @Override
//    public void showCurrentWeather(CurrentWeatherAdapterModel currentWeather) {
//        if (currentWeather != null) {
//            tvHumidity.setText(currentWeather.getHumidity());
//            tvTemperature.setText(currentWeather.getTemperature());
//            tvWind.setText(currentWeather.getWindSpeedText() + ", " + currentWeather.getWindDirectionText());
//        }
//    }

    public interface OnFragmentInteractionListener {
        void onCurrentWeatherFragmentInteraction(String message);
    }
}
