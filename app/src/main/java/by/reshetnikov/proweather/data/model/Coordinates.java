package by.reshetnikov.proweather.data.model;

import android.os.Parcel;

/**
 * Created by s-reshetnikov.
 */

public class Coordinates {

    private double latitude;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Coordinates(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
