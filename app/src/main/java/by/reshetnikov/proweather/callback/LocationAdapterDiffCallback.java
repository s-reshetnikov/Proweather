package by.reshetnikov.proweather.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

import by.reshetnikov.proweather.data.model.LocationAdapterContract;
import by.reshetnikov.proweather.data.model.LocationAdapterModel;


public class LocationAdapterDiffCallback extends DiffUtil.Callback {

    private final List<LocationAdapterModel> oldLocations;
    private final List<LocationAdapterModel> newLocations;

    public LocationAdapterDiffCallback(List<LocationAdapterModel> oldList, List<LocationAdapterModel> newList) {
        this.oldLocations = oldList;
        this.newLocations = newList;
    }

    @Override
    public int getOldListSize() {
        return oldLocations.size();
    }

    @Override
    public int getNewListSize() {
        return newLocations.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldLocations.get(oldItemPosition).getLocationId() == (newLocations.get(newItemPosition).getLocationId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        LocationAdapterContract oldLocation = oldLocations.get(oldItemPosition);
        LocationAdapterContract newLocation = newLocations.get(oldItemPosition);

        return oldLocation.getPosition() == newLocation.getPosition() &&
                oldLocation.isCurrent() == newLocation.isCurrent();
    }

}
