package by.reshetnikov.proweather.data.network.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    // Weather condition id
    @SerializedName("id")
    @Expose
    private int id;

    // Group of weather parameters (Rain, Snow, Extreme etc.)
    @SerializedName("main")
    @Expose
    private String main;

    // Weather condition within the group
    @SerializedName("description")
    @Expose
    private String description;

    // Weather icon id
    @SerializedName("icon")
    @Expose
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}