package by.reshetnikov.proweather.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.reshetnikov.proweather.callback.LocationsDiffUtilCallback;
import by.reshetnikov.proweather.contract.LocationsAdapterContract;
import by.reshetnikov.proweather.data.model.LocationAdapterContract;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;
import by.reshetnikov.proweather.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.listener.OnLocationsOrderChangedListener;
import by.reshetnikov.proweather.viewholder.LocationViewHolder;

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> implements LocationsAdapterContract {
    private final static boolean DETECT_MOVES = true;
    private List<LocationAdapterModel> locations = new ArrayList<>();
    private OnLocationsOrderChangedListener orderChangedListener = null;
    private OnLocationRemovedListener locationRemovedListener = null;

    public LocationsRecyclerViewAdapter() {
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {

        LocationAdapterContract location = getLocationAtPosition(position);
        holder.setLocationName(location.getLocationName());
        holder.setCircleCountryCode(location.getCountryCode());
        if (location.isCurrent())
            holder.markAsCurrent(true);
    }

    private LocationAdapterContract getLocationAtPosition(int position) {
        for (LocationAdapterModel model :
                locations) {
            if (model.getPosition() == position)
                return model;
        }
        throw new IndexOutOfBoundsException("Position " + position + " was not found at list");
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public void updateView(List<LocationAdapterModel> updatedLocations) {
        LocationsDiffUtilCallback diffCallback = new LocationsDiffUtilCallback(this.locations, updatedLocations);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback, DETECT_MOVES);

        locations.clear();
        locations.addAll(updatedLocations);
        diffResult.dispatchUpdatesTo(this);
        locations = updatedLocations;
    }

    @Override
    public LocationAdapterModel getLocation(int position) {
        return locations.get(position);
    }

    @Override
    public void addLocation(LocationAdapterModel location) {
        int position = locations.size();
        locations.add(position, location);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, locations.size());
    }

    @Override
    public void moveLocationItem(int fromPosition, int toPosition, boolean saveChanges) {
        if (saveChanges && orderChangedListener != null) {
            orderChangedListener.onOrderChange(fromPosition, toPosition);
            return;
        }
        Collections.swap(locations, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void removeLocation(int position) {
        if (locationRemovedListener != null) {
            locationRemovedListener.onRemove(locations.get(position));
        }
    }

    @Override
    public void setOnLocationsOrderChangedListener(OnLocationsOrderChangedListener listener) {
        this.orderChangedListener = listener;
    }

    @Override
    public void setOnLocationRemovedListener(OnLocationRemovedListener listener) {
        this.locationRemovedListener = listener;
    }
}
