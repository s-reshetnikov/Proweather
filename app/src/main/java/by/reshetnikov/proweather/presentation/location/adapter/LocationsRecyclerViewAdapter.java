package by.reshetnikov.proweather.presentation.location.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.reshetnikov.proweather.data.db.model.LocationEntity;
import by.reshetnikov.proweather.presentation.location.callback.LocationsDiffUtilCallback;
import by.reshetnikov.proweather.presentation.location.listener.OnLocationRemovedListener;
import by.reshetnikov.proweather.presentation.location.listener.OnLocationsOrderChangedListener;
import by.reshetnikov.proweather.presentation.location.viewholder.LocationViewHolder;
import timber.log.Timber;

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<LocationViewHolder> implements LocationsViewAdapterContract {

    private final static boolean DETECT_MOVES = false;
    private List<LocationEntity> locations = new ArrayList<>();
    private OnLocationsOrderChangedListener orderChangedListener = null;
    private OnLocationRemovedListener locationRemovedListener = null;

    public LocationsRecyclerViewAdapter() {
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onViewRecycled(LocationViewHolder holder) {
        super.onViewRecycled(holder);
        Timber.d("onViewRecycled(), holder pos = " + holder.getAdapterPosition());
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        LocationEntity location;
        try {
            location = getLocationAtPosition(position);
        } catch (IndexOutOfBoundsException e) {
            Timber.e("No such position at list, size is " + locations.size(), e);
            return;
        }
        holder.setLocationName(location.getLocationName());
        holder.setCircleCountryCode(location.getCountryCode());
        int firstPosition = 0;
        boolean isCurrent = location.getPosition() == firstPosition;
        holder.markAsCurrent(isCurrent);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public void updateView(List<LocationEntity> updatedLocations) {
        Timber.d("updateView(), called");
        LocationsDiffUtilCallback diffCallback = new LocationsDiffUtilCallback(this.locations, updatedLocations);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback, DETECT_MOVES);
        Timber.d("updateView(), changed locations size from " + diffCallback.getOldListSize() + " to " + diffCallback.getNewListSize());
        locations.clear();
        locations.addAll(updatedLocations);
        diffResult.dispatchUpdatesTo(this);
        locations = updatedLocations;
        Timber.d("updateView(), ends");
    }

    @Override
    public LocationEntity getLocation(int position) {
        return locations.get(position);
    }

    @Override
    public void addLocation(LocationEntity location) {
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
        Timber.d("removeLocation(), called");
        if (locationRemovedListener != null) {
            locationRemovedListener.onRemove(getLocationAtPosition(position));
        }

        Timber.d("removeLocation(), ends");
    }

    @Override
    public void setOnLocationsOrderChangedListener(OnLocationsOrderChangedListener listener) {
        this.orderChangedListener = listener;
    }

    @Override
    public void setOnLocationRemovedListener(OnLocationRemovedListener listener) {
        this.locationRemovedListener = listener;
    }

    private LocationEntity getLocationAtPosition(int position) {
        for (LocationEntity model :
                locations) {
            if (model.getPosition() == position)
                return model;
        }
        throw new IndexOutOfBoundsException("Position " + position + " was not found at list");
    }
}
