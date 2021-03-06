package by.reshetnikov.proweather.presentation.dailyforecast.viewholder;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.reshetnikov.proweather.R;
import by.reshetnikov.proweather.utils.WeatherStateIconUtil;
import timber.log.Timber;


/**
 * Created by s-reshetnikov.
 */

public class DailyForecastViewHolder extends RecyclerView.ViewHolder implements DailyForecastViewHolderContract {

    @BindView(R.id.cardConstraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.tvWeekDayAndDate)
    TextView tvDateCard;
    @BindView(R.id.tvTemperatureDetail)
    TextView tvTemperatureCard;
    @BindView(R.id.tvHumidityDataValue)
    TextView tvHumidityDataValue;
    @BindView(R.id.tvWindSpeedDataValue)
    TextView tvWindSpeedDataValue;
    @BindView(R.id.tvWindDirectionDataValue)
    TextView tvWindDirectionDataValue;
    @BindView(R.id.tvPressureDataValue)
    TextView tvPressureDataValue;
    @BindView(R.id.ivStateWeatherDetail)
    ImageView ivStateWeatherCard;

    @BindView(R.id.expandedDetailsDivider)
    View expandedDetailsDivider;
    @BindView(R.id.tvMorningTemperatureDataValue)
    TextView tvMorningTemperature;
    @BindView(R.id.tvDayTemperatureDataValue)
    TextView tvDayTemperature;
    @BindView(R.id.tvEveningTemperatureDataValue)
    TextView tvEveningTemperature;
    @BindView(R.id.tvNightTemperatureDataValue)
    TextView tvNightTemperature;

    @BindView(R.id.tvForecastStateMorning)
    TextView tvForecastStateMorning;
    @BindView(R.id.tvForecastStateDay)
    TextView tvForecastStateDay;
    @BindView(R.id.tvForecastStateEvening)
    TextView tvForecastStateEvening;
    @BindView(R.id.tvForecastStateNight)
    TextView tvForecastStateNight;

    @BindView(R.id.btnDailyCardDetails)
    Button btnDetails;
    @BindView(R.id.ivCollapsibleCardState)
    ImageView ivCollapsibleCardState;

    private boolean isExpanded = false;

    public DailyForecastViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_daily_forecast, parent, false));
        ButterKnife.bind(this, itemView);
        collapse();
    }

    @OnClick({R.id.btnDailyCardDetails, R.id.ivCollapsibleCardState})
    public void onClick(View v) {
        Timber.d("VH", "clicked");
        rotateExpandableState();
        if (isExpanded)
            collapse();
        else
            expand();
    }

    @Override
    public void setDate(String date) {
        tvDateCard.setText(date);
    }

    @Override
    public void setTemperature(String temperature) {
        tvTemperatureCard.setText(temperature);
    }

    @Override
    public void setMainImage(int stateCode) {
        boolean isDay = true;
        ivStateWeatherCard.setImageDrawable(WeatherStateIconUtil.getIcon(stateCode, isDay));
    }

    @Override
    public void setHumidity(String humidity) {
        tvHumidityDataValue.setText(humidity);
    }

    @Override
    public void setPressure(String pressure) {
        tvPressureDataValue.setText(pressure);
    }

    @Override
    public void setWindDetails(String windSpeed, String windDirection) {
        tvWindSpeedDataValue.setText(windSpeed);
        tvWindDirectionDataValue.setText(windDirection);
    }

    @Override
    public void setMorningTemperature(String temperature) {
        tvMorningTemperature.setText(temperature);
    }

    @Override
    public void setDayTemperature(String temperature) {
        tvDayTemperature.setText(temperature);
    }

    @Override
    public void setEveningTemperature(String temperature) {
        tvEveningTemperature.setText(temperature);
    }

    @Override
    public void setNightTemperature(String temperature) {
        tvNightTemperature.setText(temperature);
    }

    @Override
    public boolean isExpanded() {
        return isExpanded;
    }

    @Override
    public void expand() {
        boolean isVisible = true;
        setVisibilityForDetails(isVisible);
        isExpanded = true;
    }

    @Override
    public void collapse() {
        boolean isVisible = false;
        setVisibilityForDetails(isVisible);
        isExpanded = false;
    }

    private void rotateExpandableState() {
        int rotationDegrees = 180;
        ivCollapsibleCardState.setPivotX(ivCollapsibleCardState.getWidth() / 2);
        ivCollapsibleCardState.setPivotY(ivCollapsibleCardState.getHeight() / 2);
        ivCollapsibleCardState.setRotation(ivCollapsibleCardState.getRotation() + rotationDegrees);
    }

    void setVisibilityForDetails(boolean isVisible) {
        int visibility = View.GONE;
        if (isVisible)
            visibility = View.VISIBLE;

        expandedDetailsDivider.setVisibility(visibility);
        tvMorningTemperature.setVisibility(visibility);
        tvDayTemperature.setVisibility(visibility);
        tvEveningTemperature.setVisibility(visibility);
        tvNightTemperature.setVisibility(visibility);
        tvForecastStateMorning.setVisibility(visibility);
        tvForecastStateDay.setVisibility(visibility);
        tvForecastStateEvening.setVisibility(visibility);
        tvForecastStateNight.setVisibility(visibility);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        if (isVisible) {
            btnDetails.setText(R.string.hide_button_text);
            constraintSet.connect(R.id.btnDailyCardDetails, ConstraintSet.TOP, R.id.tvMorningTemperatureDataValue, ConstraintSet.BOTTOM);
        } else {
            btnDetails.setText(R.string.details_button_text);
            constraintSet.connect(R.id.btnDailyCardDetails, ConstraintSet.TOP, R.id.tvPressureLabel, ConstraintSet.BOTTOM);
        }

        constraintSet.applyTo(constraintLayout);
        constraintSet.applyTo(constraintLayout);
    }
}