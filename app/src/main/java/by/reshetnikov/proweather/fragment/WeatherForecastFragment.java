package by.reshetnikov.proweather.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.contract.WeatherForecastContract;
import by.reshetnikov.proweather.presenter.WeatherForecastPresenter;


public class WeatherForecastFragment extends Fragment implements WeatherForecastContract.View {

    WeatherForecastPresenter presenter;

    private OnFragmentInteractionListener mListener;

    public WeatherForecastFragment() {
    }

    public static WeatherForecastFragment newInstance() {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    }

    @Override
    public void showWeatherForecast() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onError(String message) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
