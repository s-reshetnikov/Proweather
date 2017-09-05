package by.reshetnikov.proweather.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class LocationEntity {

    @Id
    private Long Id;
    @Index(unique = true)
    private int locationId;
    @NotNull
    private String locationName;
    private String countryCode;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @NotNull
    private int position;

    @Generated(hash = 809444569)
    public LocationEntity(Long Id, int locationId, @NotNull String locationName,
                          String countryCode, double longitude, double latitude, int position) {
        this.Id = Id;
        this.locationId = locationId;
        this.locationName = locationName;
        this.countryCode = countryCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.position = position;
    }

    @Generated(hash = 1723987110)
    public LocationEntity() {
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
