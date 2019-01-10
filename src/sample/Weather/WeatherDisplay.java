package sample.Weather;

import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class is responsible for updating server queries
 */
public class WeatherDisplay implements Observer {

    private String city;
    private Weather weather;
    private ObservableList<Weather> weatherList;
    private ArrayList<String> chartTimeList;





    public WeatherDisplay(String city, ObservableList<Weather> weatherList,ArrayList<String> chartTimeList) {
        this.city = city;
        this.weatherList = weatherList;
        this.chartTimeList = chartTimeList;

    }

    /**4
     *The method returns the weather class object
     * @return weather
     */
    public Weather getWeather() {
        return weather;
    }

    @Override
    public void updateWeather(Weather weather) {
        this.weather = weather;
        weatherList.add(weather);
        chartTimeList.add(LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm")));
        System.out.println("Rozmiar " + weatherList.size());
    }

    @Override
    public String getCity() {
        return city;
    }

}
