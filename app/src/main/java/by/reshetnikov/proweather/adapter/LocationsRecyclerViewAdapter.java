package by.reshetnikov.proweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.reshetnikov.proweather.contract.LocationManagerContract;
import by.reshetnikov.proweather.contract.LocationsAdapterContract;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.viewholder.LocationViewHolder;
import io.reactivex.disposables.CompositeDisposable;

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> implements LocationsAdapterContract {

    private WeakReference<LocationManagerContract.Presenter> presenterRef;
    private List<LocationAppModel> locations = new ArrayList<>();
    private CompositeDisposable compositeDisposable;

    public LocationsRecyclerViewAdapter(LocationManagerContract.Presenter presenter) {
        presenterRef = new WeakReference<>(presenter);
        compositeDisposable = new CompositeDisposable();
        loadLocations();
    }

    private void loadLocations() {
        List<LocationAppModel> locations = getPresenter().getLocations();
        if (locations.size() == 0) {
            locations.addAll(locations);
            return;
        }

        for (LocationAppModel location : locations) {
            if (!locations.contains(location))
                locations.add(location);
        }
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        LocationAppModel location = locations.get(position);
        holder.setLocationName(location.getLocationName());
        holder.setCountryCode(location.getCountryCode());
        if (location.isCurrent())
            holder.markAsCurrent(true);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public void updateView(List<LocationAppModel> locations) {

    }

    @Override
    public void removeLocation(int position) {
        getPresenter().removeLocation(getLocation(position));
        // locations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, locations.size());
    }

    @Override
    public void onLocationItemMove(int fromPosition, int toPosition) {
        Collections.swap(locations, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void addLocation(LocationAppModel location) {
        int position = locations.size();
        locations.add(position, location);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, locations.size());
    }

    @Override
    public LocationAppModel getLocation(int position) {
        return locations.get(position);
    }

    private LocationManagerContract.Presenter getPresenter() {
        if (presenterRef != null)
            return presenterRef.get();
        throw new NullPointerException("Presenter was disposed!!!");
    }
}
