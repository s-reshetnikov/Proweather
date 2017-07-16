package by.reshetnikov.proweather.data.model;


interface CurrentWeatherModelContract {

    void applyUnits(UnitsAppModel units);

    double getTemperature();

    void setTemperature(double temperature);

    int getHumidity();

    void setHumidity(int humidity);

    double getWindSpeed();

    void setWindSpeed(double windSpeed);

    int getPressure();

    void setPressure(int pressure);

    int getWeatherConditionId();

    void setWeatherConditionId(int weatherConditionId);

    String getWeatherDescription();

    void setWeatherDescription(String weatherDescription);

    String getIconCode();

    void setIconCode(String iconCode);

    double getWindDegrees();

    void setWindDegrees(double windDegrees);

    double getRain();

    void setRain(double rain);

    double getSnow();

    void setSnow(double snow);


}
