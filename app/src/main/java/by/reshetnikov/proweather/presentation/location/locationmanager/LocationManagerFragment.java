package by.reshetnikov.proweather.presentation.location.locationmanager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.di.component.ActivityComponent;
import by.reshetnikov.proweather.di.component.DaggerActivityComponent;
import by.reshetnikov.proweather.di.module.ActivityModule;
import by.reshetnikov.proweather.presentation.customview.DelayAutoCompleteTextView;
import by.reshetnikov.proweather.presentation.decoration.SimpleDividerItemDecoration;
import by.reshetnikov.proweather.presentation.location.LocationActivity;
import by.reshetnikov.proweather.presentation.location.locationmanager.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.presentation.location.locationmanager.adapter.LocationsViewAdapterContract;
import by.reshetnikov.proweather.presentation.location.locationmanager.callback.LocationItemTouchHelperCallback;
import by.reshetnikov.proweather.presentation.location.locationmanager.callback.LocationManagerCallback;
import by.reshetnikov.proweather.presentation.location.locationmanager.listener.OnAutoCompleteLocationSearchListener;
import by.reshetnikov.proweather.presentation.location.locationmanager.listener.OnLocationClickedListener;
import by.reshetnikov.proweather.presentation.location.locationmanager.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.presentation.location.locationmanager.listener.OnLocationsOrderChangedListener;
import by.reshetnikov.proweather.utils.ToastUtils;
import timber.log.Timber;

public class LocationManagerFragment extends Fragment implements LocationManagerContract.View, LocationManagerCommunication {

    @BindView(R.id.rv_locations)
    RecyclerView rvLocations;
    @BindView(R.id.ac_location_search)
    DelayAutoCompleteTextView tvAutoCompleteLocation;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    @Nullable
    @BindView(R.id.fab_show_map)
    FloatingActionButton fabShowMap;
    @Inject
    LocationManagerContract.Presenter presenter;
    private ActivityComponent component;
    private LocationManagerCallback locationManagerCallback;

    public LocationManagerFragment() {
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        component = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule((AppCompatActivity) this.getActivity()))
                .applicationComponent(((ProWeatherApp) getActivity().getApplication()).getComponent())
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location_manager, container, false);
        component.inject(this);
        presenter.setView(this);
        ButterKnife.bind(this, view);
        setupAutoCompleteView();
        setupLocationsRecyclerView();
        return view;
    }

    @Optional
    @OnClick(R.id.fab_show_map)
    void onOpenMapClick() {
        presenter.onFabClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Timber.d("onAttach() called");
        if (context instanceof LocationActivity) {
            component = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule((AppCompatActivity) context))
                    .applicationComponent(((ProWeatherApp) getActivity().getApplication()).getComponent())
                    .build();
        }
        try {
            locationManagerCallback = (LocationManagerCallback) context;
        } catch (ClassCastException e) {
            Timber.e(e);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Timber.d("view created");
        super.onViewCreated(view, savedInstanceState);
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void openMap() {
        locationManagerCallback.onOpenMapClicked();
    }

    @Override
    public void refreshSavedLocations(List<LocationEntity> savedLocations) {
        LocationsRecyclerViewAdapter rvAdapter = (LocationsRecyclerViewAdapter) rvLocations.getAdapter();
        rvAdapter.updateView(savedLocations);
    }

    @Override
    public void refreshSearchedLocations(List<LocationEntity> locations) {
        AutoCompleteLocationsAdapter adapter = (AutoCompleteLocationsAdapter) tvAutoCompleteLocation.getAdapter();
        adapter.updateSearchResults(locations);
    }

    @Override
    public void showError(String errorText) {
        ToastUtils.showToast(Toast.makeText(this.getContext(), errorText, Toast.LENGTH_SHORT));
    }

    public void setupLocationsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvLocations.setLayoutManager(layoutManager);
        LocationsRecyclerViewAdapter locationsAdapter = getLocationsRecyclerViewAdapter();
        rvLocations.setAdapter(locationsAdapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLocations.addItemDecoration(getDividerItemDecoration());
        setupItemTouchHelper();
    }

    @NonNull
    private LocationsRecyclerViewAdapter getLocationsRecyclerViewAdapter() {
        LocationsRecyclerViewAdapter locationsAdapter = new LocationsRecyclerViewAdapter();
        setOnLocationItemClickedListener(locationsAdapter);
        setOnLocationOrderChangeListener(locationsAdapter);
        setOnLocationRemovedListener(locationsAdapter);
        return locationsAdapter;
    }

    private void setOnLocationItemClickedListener(LocationsRecyclerViewAdapter locationsAdapter) {
        locationsAdapter.setOnLocationClickedListener(new OnLocationClickedListener() {
            @Override
            public void onLocationItemClicked(LocationEntity location) {
                presenter.onLocationClicked(location);
                locationManagerCallback.onLocationClicked(location);
            }
        });
    }

    private void setOnLocationOrderChangeListener(LocationsRecyclerViewAdapter locationsAdapter) {
        locationsAdapter.setOnLocationsOrderChangedListener(new OnLocationsOrderChangedListener() {
            @Override
            public void onOrderChange(int fromPosition, int toPosition) {
                Timber.d("onLocationsOrderChangedListener, from " + fromPosition + " to " + toPosition);
                presenter.onLocationItemMoved(fromPosition, toPosition);
                locationManagerCallback.onLocationsChanged();
            }
        });
    }

    private void setOnLocationRemovedListener(LocationsRecyclerViewAdapter locationsAdapter) {
        locationsAdapter.setOnLocationRemovedListener(new OnLocationRemovedListener() {
            @Override
            public void onRemove(LocationEntity location) {
                String message = "onRemoveLocationListener, (" + location.getLocationId() + ") - " + location.getLocationName() +
                        " " + location.getPosition();
                Timber.d(message);
                presenter.onLocationItemRemoved(location);
                locationManagerCallback.onLocationRemoved();
            }
        });
    }

    @NonNull
    private SimpleDividerItemDecoration getDividerItemDecoration() {
        Drawable divider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        int paddingLeftPx = (int) getResources().getDimension(R.dimen.item_view_with_image_margin_left);
        return new SimpleDividerItemDecoration(rvLocations.getContext(), divider, paddingLeftPx);
    }

    private void setupItemTouchHelper() {
        final LocationsViewAdapterContract adapter = (LocationsViewAdapterContract) rvLocations.getAdapter();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new LocationItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rvLocations);
    }

    private void setupAutoCompleteView() {
        AutoCompleteLocationsAdapter adapter = new AutoCompleteLocationsAdapter(this.getContext());
        tvAutoCompleteLocation.setLoadingIndicator(progressBar);
        setAutoCompleteTextViewOnPerformSearchListener(adapter);
        setAutoCompleteTextViewOnClickListener();
        tvAutoCompleteLocation.setAdapter(adapter);
    }

    private void setAutoCompleteTextViewOnPerformSearchListener(AutoCompleteLocationsAdapter adapter) {
        adapter.setOnPerformSearchListener(new OnAutoCompleteLocationSearchListener() {
            @Override
            public void performSearch(String searchText) {
                presenter.onSearchLocationByName(searchText);
            }
        });
    }

    private void setAutoCompleteTextViewOnClickListener() {
        tvAutoCompleteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                LocationEntity location = (LocationEntity) adapterView.getItemAtPosition(position);
                Toast.makeText(view.getContext(), location.getLocationName(), Toast.LENGTH_LONG).show();
                tvAutoCompleteLocation.setText("");
                presenter.onLocationFromDropDownClicked(location);
                locationManagerCallback.onLocationAdded(location);
            }
        });
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void updateLocationsList() {
        presenter.onLocationsRefreshed();
    }
}