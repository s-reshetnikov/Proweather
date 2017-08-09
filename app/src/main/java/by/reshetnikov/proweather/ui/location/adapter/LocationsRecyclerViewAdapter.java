package by.reshetnikov.proweather.ui.location.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.reshetnikov.proweather.data.model.location.LocationContract;
import by.reshetnikov.proweather.ui.location.callback.LocationsDiffUtilCallback;
import by.reshetnikov.proweather.ui.location.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.ui.location.listener.OnLocationsOrderChangedListener;
import by.reshetnikov.proweather.ui.location.viewholder.LocationViewHolder;

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> implements LocationsViewAdapterContract {
    private final static boolean DETECT_MOVES = true;
    private List<LocationContract> locations = new ArrayList<>();
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

        LocationContract location = getLocationAtPosition(position);
        holder.setLocationName(location.getLocationName());
        holder.setCircleCountryCode(location.getCountryCode());
        if (location.isCurrent())
            holder.markAsCurrent(true);
    }

    private LocationContract getLocationAtPosition(int position) {
        for (LocationContract model :
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
    public void updateView(List<LocationContract> updatedLocations) {
        LocationsDiffUtilCallback diffCallback = new LocationsDiffUtilCallback(this.locations, updatedLocations);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback, DETECT_MOVES);

        locations.clear();
        locations.addAll(updatedLocations);
        diffResult.dispatchUpdatesTo(this);
        locations = updatedLocations;
    }

    @Override
    public LocationContract getLocation(int position) {
        return locations.get(position);
    }

    @Override
    public void addLocation(LocationContract location) {
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
