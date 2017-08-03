package by.reshetnikov.proweather.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.contract.WeatherForecastContract;
import by.reshetnikov.proweather.presenter.WeatherForecastPresenter;


public class HourlyForecastFragment extends Fragment implements WeatherForecastContract.View {

    private static final String ARG_SECTION_NUMBER = "SECTION_NUMBER";

    WeatherForecastPresenter presenter;

    private OnFragmentInteractionListener mListener;

    public HourlyForecastFragment() {
    }

    public static HourlyForecastFragment newInstance(int sectionNumber) {
        HourlyForecastFragment fragment = new HourlyForecastFragment();
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
