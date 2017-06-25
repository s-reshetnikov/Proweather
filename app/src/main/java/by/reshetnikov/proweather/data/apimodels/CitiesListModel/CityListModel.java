package by.reshetnikov.proweather.data.apimodels.CitiesListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityListModel {

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("count")
    @Expose
    public int count;

    @SerializedName("list")
    @Expose
    public List<CityListModel> list = null;

}
