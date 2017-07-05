package by.reshetnikov.proweather.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.adapter.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.callbacks.ItemTouchHelperLocationsCallback;
import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.contract.LocationsAdapterContract;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.presenter.LocationManagerPresenter;
import by.reshetnikov.proweather.view.DelayAutoCompleteTextView;

public class LocationManagerFragment extends Fragment implements LocationManagerContract.View {

    @BindView(R.id.rv_locations)
    RecyclerView rvLocations;
    @BindView(R.id.ac_location_search)
    DelayAutoCompleteTextView autoCompleteLocation;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    private LocationManagerContract.Presenter presenter;

    public LocationManagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location, container, false);
        presenter = new LocationManagerPresenter();
        //presenter.setView((LocationManagerContract.View) view);

        ButterKnife.bind(this, view);

        setupLocationsRecyclerView();

        setupAutoCompleteView();

        return view;
    }

    private void setupLocationsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvLocations.setLayoutManager(layoutManager);
        LocationsRecyclerViewAdapter locationsAdapter = new LocationsRecyclerViewAdapter(presenter.getLocations());
        rvLocations.setAdapter(locationsAdapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupItemTouchHelper();
    }

    private void setupAutoCompleteView() {
        autoCompleteLocation.setAdapter(new AutoCompleteLocationsAdapter(this.getContext(), autoCompleteLocation.getThreshold()));
        autoCompleteLocation.setLoadingIndicator(progressBar);
        setAutoCompleteOnClickListener();
    }

    private void setAutoCompleteOnClickListener() {
        autoCompleteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                LocationAppModel location = (LocationAppModel) adapterView.getItemAtPosition(position);
                Toast.makeText(view.getContext(), location.getLocationName(), Toast.LENGTH_LONG).show();
                autoCompleteLocation.setVisibility(View.GONE);
                presenter.saveLocation(location);
                rvLocations.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showSearchLocation() {

    }

    @Override
    public void hideSearchLocation() {

    }

    private void setupItemTouchHelper() {
        LocationsAdapterContract adapterContract = (LocationsAdapterContract) rvLocations.getAdapter();
        ItemTouchHelperLocationsCallback locationsCallback =
                new ItemTouchHelperLocationsCallback(0, ItemTouchHelper.RIGHT, adapterContract);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(locationsCallback);
        itemTouchHelper.attachToRecyclerView(rvLocations);
    }

}
