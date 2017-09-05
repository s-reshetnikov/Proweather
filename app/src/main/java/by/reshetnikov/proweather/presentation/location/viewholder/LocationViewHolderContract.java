package by.reshetnikov.proweather.presentation.location.viewholder;

/**
 * Created by SacRahl on 7/14/2017.
 */

interface LocationViewHolderContract {
    void setLocationName(String locationName);

    void setCircleCountryCode(String countryCode);

    void markAsCurrent(boolean isCurrent);

    void onItemSelected();

    void onItemClear();
}
