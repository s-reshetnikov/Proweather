package by.reshetnikov.proweather.fragment;

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
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.ProWeatherApp;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.adapter.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.callback.LocationItemTouchHelperCallback;
import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.contract.LocationsAdapterContract;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.decoration.SimpleDividerItemDecoration;
import by.reshetnikov.proweather.injector.component.ActivityComponent;
import by.reshetnikov.proweather.injector.component.DaggerActivityComponent;
import by.reshetnikov.proweather.injector.module.ActivityModule;
import by.reshetnikov.proweather.listener.OnAutoCompleteLocationSearchListener;
import by.reshetnikov.proweather.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.listener.OnLocationsOrderChangedListener;
import by.reshetnikov.proweather.utils.ToastUtils;
import by.reshetnikov.proweather.view.DelayAutoCompleteTextView;

public class LocationManagerFragment extends Fragment implements LocationManagerContract.View {

    private static final String TAG = LocationManagerFragment.class.getSimpleName();

    @BindView(R.id.rv_locations)
    RecyclerView rvLocations;
    @BindView(R.id.ac_location_search)
    DelayAutoCompleteTextView tvAutoCompleteLocation;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    @BindView(R.id.fab_add_location)
    FloatingActionButton fabAddLocation;
    @Inject
    LocationManagerContract.Presenter presenter;
    private ActivityComponent component;

    public LocationManagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location, container, false);
        component.inject(this);
        presenter.setView(this);
        ButterKnife.bind(this, view);
        setupAutoCompleteView();
        setupLocationsRecyclerView();
        setupAddLocationButton();
        return view;
    }

    private ActivityComponent getActivityComponent() {
        return null;
    }

    private void setupAddLocationButton() {
        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFabClicked(tvAutoCompleteLocation.getVisibility() == TextView.VISIBLE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called");
        if (context instanceof AppCompatActivity) {
            component = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule((AppCompatActivity) context))
                    .applicationComponent(((ProWeatherApp) getActivity().getApplication()).getComponent())
                    .build();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showSearchLocation() {
        tvAutoCompleteLocation.setVisibility(View.VISIBLE);
        tvAutoCompleteLocation.requestFocus();
        fabAddLocation.setVisibility(View.GONE);
    }

    @Override
    public void hideSearchLocation() {
        tvAutoCompleteLocation.setVisibility(View.GONE);
        fabAddLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshSavedLocations(List<LocationAdapterModel> savedLocations) {
        LocationsRecyclerViewAdapter rvAdapter = (LocationsRecyclerViewAdapter) rvLocations.getAdapter();
        rvAdapter.updateView(savedLocations);
    }

    @Override
    public void refreshSearchedLocations(List<LocationAdapterModel> locations) {
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
        LocationsRecyclerViewAdapter locationsAdapter = new LocationsRecyclerViewAdapter();
        locationsAdapter.setOnLocationsOrderChangedListener(new OnLocationsOrderChangedListener() {
            @Override
            public void onOrderChange(int fromPosition, int toPosition) {
                Log.d(TAG, "onOrderChange listener from " + fromPosition + " to " + toPosition);
                presenter.onLocationItemMoved(fromPosition, toPosition);
            }
        });
        locationsAdapter.setOnLocationRemovedListener(new OnLocationRemovedListener() {
            @Override
            public void onRemove(LocationAdapterModel location) {
                presenter.onLocationItemRemoved(location);
            }
        });
        rvLocations.setAdapter(locationsAdapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLocations.addItemDecoration(getDividerItemDecoration());
        setupItemTouchHelper();
    }

    @NonNull
    private SimpleDividerItemDecoration getDividerItemDecoration() {
        Drawable divider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        int paddingLeftPx = (int) getResources().getDimension(R.dimen.item_view_with_image_margin_left);
        return new SimpleDividerItemDecoration(rvLocations.getContext(), divider, paddingLeftPx);
    }

    private void setupItemTouchHelper() {
        final LocationsAdapterContract adapter = (LocationsAdapterContract) rvLocations.getAdapter();
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
                presenter.onLocationByNameSearch(searchText);
            }
        });
    }

    private void setAutoCompleteTextViewOnClickListener() {
        tvAutoCompleteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                LocationAdapterModel location = (LocationAdapterModel) adapterView.getItemAtPosition(position);
                Toast.makeText(view.getContext(), location.getLocationName(), Toast.LENGTH_LONG).show();
                tvAutoCompleteLocation.setText("");
                hideSearchLocation();
                presenter.saveLocation(location);
            }
        });
        rvLocations.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                Log.d(TAG, event.toString());
                return false;
            }
        });
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onError(String message) {

    }
}