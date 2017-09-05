package by.reshetnikov.proweather.presentation.location.viewholder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.utils.ResourcesUtil;

public class LocationViewHolder extends RecyclerView.ViewHolder implements LocationViewHolderContract {

    @BindView(R.id.tv_item_location_name)
    protected TextView tvLocationName;
    @BindView(R.id.tv_item_first_symbols)
    protected TextView tvCircleIcon;
    @BindView(R.id.defaultLocationMark)
    ImageButton defaultMark;
    @BindView(R.id.item_location)
    RelativeLayout layout;

    private boolean isPressed = false;

    public LocationViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_location, parent, false));
        ButterKnife.bind(this, itemView);
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
        if (isCurrent)
            defaultMark.setImageDrawable(ResourcesUtil.getDrawable(R.drawable.ic_star_filled_24dp));
        else
            defaultMark.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(ResourcesUtil.getColor(R.color.colorGreyLight));
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(Color.TRANSPARENT);
    }
}
