package by.reshetnikov.proweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.adapter.AutoCompleteLocationsAdapter;
import by.reshetnikov.proweather.adapter.LocationsRecyclerViewAdapter;
import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.presenter.LocationManagerPresenter;
import by.reshetnikov.proweather.view.DelayAutoCompleteTextView;

public class LocationManagerFragment extends Fragment implements LocationManagerContract.View {

    private static final String TAG = LocationManagerFragment.class.getSimpleName();

    @BindView(R.id.rv_locations)
    RecyclerView rvLocations;
    @BindView(R.id.ac_location_search)
    DelayAutoCompleteTextView autoCompleteLocation;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar progressBar;
    //    @BindView(R.id.fab_add_location)
//    FloatingActionButton btnAddLocation;
    private LocationManagerContract.Presenter presenter;

    public LocationManagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_location, container, false);
        presenter = new LocationManagerPresenter();
        presenter.setView(this);

        ButterKnife.bind(this, view);


//        btnAddLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSearchLocation();
//            }
//        });

        setupLocationsRecyclerView();

        setupAutoCompleteView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton button = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_location);
        if (button != null) {
            System.out.println("Wow!");
        }
    }

    @Override
    public void showSearchLocation() {
        autoCompleteLocation.setVisibility(View.VISIBLE);
//        btnAddLocation.setVisibility(View.GONE);
    }

    @Override
    public void hideSearchLocation() {
        autoCompleteLocation.setVisibility(View.GONE);
//        btnAddLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshSavedLocations(List<LocationAppModel> savedLocations) {

    }

    private void setupLocationsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvLocations.setLayoutManager(layoutManager);
        LocationsRecyclerViewAdapter locationsAdapter = new LocationsRecyclerViewAdapter(presenter);
        rvLocations.setAdapter(locationsAdapter);
        rvLocations.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupItemTouchHelper();
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                int position = viewHolder.getAdapterPosition();
                int oldPosition = viewHolder.getOldPosition();
                presenter.onLocationItemMoved(position, oldPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                presenter.onLocationItemDeleted(position);
                viewHolder.setIsRecyclable(false);
            }
        });
        itemTouchHelper.attachToRecyclerView(rvLocations);
    }

    private void setupAutoCompleteView() {
        autoCompleteLocation.setAdapter(new AutoCompleteLocationsAdapter(this.getContext(), presenter));
        autoCompleteLocation.setLoadingIndicator(progressBar);
        setAutoCompleteOnClickListener();
    }

    private void setAutoCompleteOnClickListener() {
        autoCompleteLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                LocationAppModel location = (LocationAppModel) adapterView.getItemAtPosition(position);
                Toast.makeText(view.getContext(), location.getLocationName(), Toast.LENGTH_LONG).show();
                presenter.saveLocation(location);
                rvLocations.getAdapter().notifyDataSetChanged();
                hideSearchLocation();
            }
        });
    }


}
