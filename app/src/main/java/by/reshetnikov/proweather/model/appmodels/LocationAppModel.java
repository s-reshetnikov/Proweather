package by.reshetnikov.proweather.model.appmodels;


public class LocationAppModel {

    private String id;
    private String cityName;
    private String countryCode;
    private String longitude;
    private String latitude;


    LocationAppModel(String id, String cityName) {
        this.id = id;
        this.cityName = cityName;
    }

    public String getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
