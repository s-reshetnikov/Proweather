package by.reshetnikov.proweather.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.R;

public class LocationViewHolder extends RecyclerView.ViewHolder implements LocationViewHolderContract {

    private static final String TAG = LocationViewHolder.class.getSimpleName();
    @BindView(R.id.tv_item_location_name)
    protected TextView tvLocationName;
    @BindView(R.id.tv_item_first_symbols)
    protected TextView tvCircleIcon;
    @BindView(R.id.item_location)
    RelativeLayout layout;

    public LocationViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_location, parent, false));
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Clicked # " + getAdapterPosition();
                Log.d(TAG, message);
            }
        });

        itemView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                String message = "Dragged # " + getAdapterPosition();
                Log.d(TAG, message);
                return false;
            }
        });
    }

    @Override
    public void setLocationName(String locationName) {
        tvLocationName.setText(locationName);
    }

    @Override
    public void setCircleCountryCode(String countryCode) {
        tvCircleIcon.setText(countryCode);
    }

    @Override
    public void markAsCurrent(boolean isCurrent) {
    }
}
