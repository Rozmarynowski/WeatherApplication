package sample.Weather;

public class Weather {

    private double temp;
    private double pressure;
    private double humidity;
    private double temp_min;
    private double temp_max;
    private String description;


    public Weather(double temp, double pressure, double humidity, double temp_min, double temp_max, String description) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.description = description;

    }

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public String getDescription() {
        description = "\""+description+"\"";
        return description; }

    @Override
    public String toString() {
        return "Temperature: " + temp + "\n" +
                "Pressure: " + pressure + "\n" +
                "Humidity: " + humidity + "\n" +
                "Max temperature: " + temp_max + "\n" +
                "Min temperature: " + temp_min + "\n" +
                "Description: " + description;
    }
}
