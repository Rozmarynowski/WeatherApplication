package sample.Weather;


import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class to getting data from Open Weather Map servers.
 */

public class WeatherStation {


    /**
     * Connects to Open weather server.
     * Downloads Json Data with information about weather.
     *
     * @param City
     * @return Weather object
     * @throws IOException
     */

    public static Weather getWeatherFromCity(String City) throws IOException {

        String httpCity = City.replace(' ', '+');

        StringBuffer response = new StringBuffer();
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + httpCity + "&units=metric&APPID=48ee905cfea07e1a3e313ac4091d723e";


        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Response: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        while ((input = in.readLine()) != null) {
            response.append(input);
        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(response.toString());
        JsonObject jsonObject = jsonTree.getAsJsonObject();
        JsonObject main = jsonObject.getAsJsonObject("main");

        JsonElement weatherArray = jsonObject.getAsJsonArray("weather");
        JsonObject weatherArrayZero = (JsonObject) ((JsonArray) weatherArray).get(0);
        JsonElement description = weatherArrayZero.get("description");
        main.add("description", description);

        Weather weather = gson.fromJson(main, Weather.class);

        System.out.println("WEATHER " + weather);


        return weather;
    }

}