package by.reshetnikov.proweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import by.reshetnikov.proweather.contract.LocationsAdapterContract;
import by.reshetnikov.proweather.model.appmodels.LocationAppModel;
import by.reshetnikov.proweather.viewholder.ViewHolder;

public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> implements LocationsAdapterContract {

    private List<LocationAppModel> locations;


    public LocationsRecyclerViewAdapter(List<LocationAppModel> locations) {
        this.locations = locations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
    public void removeLocation(int position) {
        locations.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, locations.size());
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
}
