package by.reshetnikov.proweather.presentation.dailyforecast;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.di.component.ActivityComponent;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.presentation.dailyforecast.adapter.DailyForecastRecyclerViewAdapter;
import by.reshetnikov.proweather.utils.ToastUtils;
import timber.log.Timber;


public class DailyForecastFragment extends Fragment implements DailyForecastContract.View {

    private static final String ARG_SECTION_NUMBER = "SECTION_NUMBER";

    @Inject
    DailyForecastPresenter presenter;
    ActivityComponent component;
    @BindView(R.id.rvForecastList)
    RecyclerView rvForecastList;
    @BindView(R.id.swipeNowForecast)
    SwipeRefreshLayout refreshLayout;

    public DailyForecastFragment() {
    }

    public static DailyForecastFragment newInstance(int sectionNumber) {
        DailyForecastFragment fragment = new DailyForecastFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.d("onAttach() called");
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
        Timber.d("onCreateView() called");
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        component.inject(this);
        ButterKnife.bind(this, view);
        setupForecastsRecyclerView();
        setupSwipeToRefresh();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d("onStart() called");
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
        Timber.d("onDetach called");
        super.onDetach();
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showToast(Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT));
    }

    @Override
    public void onError(String message) {
        ToastUtils.showToast(Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT));
    }

    @Override
    public void showWeatherForecast(List<DailyForecastViewModel> forecast) {
        Timber.d("showWeatherForecast() called");
        DailyForecastRecyclerViewAdapter adapter = (DailyForecastRecyclerViewAdapter) rvForecastList.getAdapter();
        adapter.updateForecast(forecast);
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    private void setupForecastsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvForecastList.setLayoutManager(layoutManager);
        DailyForecastRecyclerViewAdapter dailyForecastsAdapter = new DailyForecastRecyclerViewAdapter();
        rvForecastList.setAdapter(dailyForecastsAdapter);
        rvForecastList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupSwipeToRefresh() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
