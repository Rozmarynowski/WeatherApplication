package sample.Weather;

import java.util.ArrayList;

public interface Observer {

    void updateWeather(Weather weather);
    String getCity();
}
