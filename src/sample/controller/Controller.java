package sample.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.control.Label;
import sample.Weather.Weather;
import sample.Weather.WeatherDisplay;
import sample.Weather.WeatherThread;
import sample.clock.AtomicClock;
import sample.clock.SimpleDigital;





public class Controller {

    private WeatherThread weatherThread = new WeatherThread();
    private WeatherDisplay weatherDisplay;
    private String weatherImage;
    private Image image;
    private Statistic statistic;
    private double x, y;
    private String currentTown = "empty";
    private ObservableList<LocalTime> timeList = FXCollections.observableArrayList();
    private ObservableList<LocalDate> dateList = FXCollections.observableArrayList();
    private ObservableList<Weather> weatherList = FXCollections.observableArrayList();
    private ArrayList<String> timeListChart = new ArrayList<>();
    private XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> humiSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> pressSeries = new XYChart.Series<>();
    private Weather weather;
    private ObservableList<Statistic> statisticsList = FXCollections.observableArrayList();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    ImageChooser imageChooser = new ImageChooser();
    ArrayList<Weather> weatherArray;

    @FXML
    private Label pressureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Button statiticButton;
    @FXML
    private Button openBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private ImageView imageLabel;
    @FXML
    private TextField userTown;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button stopBtn;
    @FXML
    private Button playBtn;
    @FXML
    private Button exitButton;
    @FXML
    private Button chartBtn;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label tempLabel;





    @FXML
    public void initialize() {

        AtomicClock clock = new AtomicClock();
        SimpleDigital display = new SimpleDigital("Clock1", "Date", timeList, dateList);
        clock.addObservere(display);
        clock.start();

        timeList.addListener((ListChangeListener<LocalTime>) c -> {
            c.next();
            if (c.wasAdded()) {
                updateTimeLabel();
            }
        });

        weatherList.addListener((ListChangeListener<Weather>) c -> {
            c.next();
            if (c.wasAdded()) {
                updateLabel();
                updateWeatherChart();

            }
        });

    }

    /**
     * Closes the controller window to the action of pressing the Button
     * @param actionEvent
     */


    public void pressButton(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }


    /**
     *
     * Gets weather objects from observableList
     * Updates labels of temperature, pressure and humidity.
     * Launches the method that selects weather images
     *
     */


    public void updateLabel() {

        Platform.runLater(() -> {

            System.out.println("SIZE " + weatherList.size());
            weather = weatherList.get(weatherList.size() - 1);

            String tempDouble = (String.valueOf(Math.round(weather.getTemp())));
            String pressure = String.valueOf(Math.round(weather.getPressure()));
            String humidity = String.valueOf(Math.round(weather.getHumidity()));

            tempLabel.textProperty().set(tempDouble);
            pressureLabel.textProperty().set(pressure + " hPa");
            humidityLabel.textProperty().set(humidity + " %");

            weatherImage = weather.getDescription();
            System.out.println("IMAGE " + weatherImage);


            imageChooser.setImageChoose(weatherImage.replaceAll("^\"|\"$", ""));
            setImage();

            updateStatisticController();

        });
    }

    /**
     * Updates label of time and data.

     */
    public void updateTimeLabel() {
        Platform.runLater(() -> {
            LocalTime time = timeList.get(timeList.size() - 1);
            LocalDate date = dateList.get(dateList.size() - 1);

            timeLabel.textProperty().set(time.format(dtf));
            dateLabel.textProperty().set(date.toString());

        });
    }

    /**
     *
     * Calculates statistical values such as means and standard deviation of temperature, pressure humidity.
     * Creates a Statsistic class object and add it to observableList statisticList
     *
     *
     */

    public void updateStatisticController() {

        double meanTemp = 0;
        double meanPressure = 0;
        double meanHumidity = 0;
        double stdTemp = 0;
        double stdPressure = 0;
        double stdHumidity = 0;

        for (Weather w : weatherList) {
            meanTemp += w.getTemp();
            meanPressure += w.getPressure();
            meanHumidity += w.getHumidity();

        }
        meanTemp /= weatherList.size();
        meanPressure /= weatherList.size();
        meanHumidity /= weatherList.size();

        for (Weather w : weatherList) {
            stdTemp += Math.pow(w.getTemp() - meanTemp, 2);
            stdPressure += Math.pow(w.getPressure() - meanPressure, 2);
            stdHumidity += Math.pow(w.getHumidity() - meanHumidity, 2);
        }

        stdTemp = Math.sqrt(stdTemp / weatherList.size());
        stdPressure = Math.sqrt(stdPressure / weatherList.size());
        stdHumidity = Math.sqrt(stdHumidity / weatherList.size());

        double tempMax = weather.getTemp_max();
        double tempMin = weather.getTemp_min();

        statistic = new Statistic(weatherList.size(), meanTemp, meanPressure, stdTemp, stdPressure, meanHumidity, stdHumidity, tempMax, tempMin);
        statisticsList.add(statistic);

    }

    /**
     *
     * Opens new window and pass there chart series.
     * @param actionEvent
     * @throws Exception
     */

    public void chartButton(javafx.event.ActionEvent actionEvent) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chartSample.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(true);

        ChartController chartController = fxmlLoader.getController();
        chartController.setChart(tempSeries, humiSeries, pressSeries);

        root.setOnMousePressed(event -> {

            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {

            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.showAndWait();
    }

    /**
     *
     * Updates series of charts by thread.
     *
     */


    private void updateWeatherChart() {

        Platform.runLater(() -> {
            for (int i = 0; i < weatherList.size(); i++) {
                tempSeries.getData().add(new XYChart.Data<>(timeListChart.get(i), weatherList.get(i).getTemp()));
                humiSeries.getData().add(new XYChart.Data<>(timeListChart.get(i), weatherList.get(i).getHumidity()));
                pressSeries.getData().add(new XYChart.Data<>(timeListChart.get(i), weatherList.get(i).getPressure()));
            }
        });
    }

    /**
     * Pauses weather thread, but only if it was not already paused.
     * @param actionEvent
     */

    public void pressPauseBtn(javafx.event.ActionEvent actionEvent) {

        if (weatherThread.isPausing() == false && weatherThread.isRunning() == true) {
            weatherThread.clickPause();
            System.out.println("GUZIK PAUZA");

        } else {
            System.out.println("GUZIK - JUŻ ZAPAUZOWANO");
        }

    }

    /**
     * Stops weather thread, but only if it was not already stoped.
     * Clear all lists and resets all labels
     * @param actionEvent
     */

    public void pressStopBtn(javafx.event.ActionEvent actionEvent) {

        userTown.textProperty().set("");
        userTown.setPromptText("TOWN");
        currentTown = "stop";

        if (weatherThread.isRunning() == true || weatherThread.isPausing() == true) {

            weatherThread.stop();
            System.out.println("GUZIK STOP");

            resetStatistic();
            tempLabel.setText("0");
            pressureLabel.setText("0");
            humidityLabel.setText("0");

        } else {
            System.out.println("APLIKACJA JEST ZASTOPOWANA");
        }
    }

    /**
     *  Takes name of Town from userTown textField.
     *  Starts new weather thread, but only if it was not already started.
     *  Changes old objects of lists on new ones.
     *
     * @param actionEvent
     */


    public void pressPlayBtn(javafx.event.ActionEvent actionEvent) {

        String town = userTown.getText();

        if (!town.equals(currentTown) || weatherThread.isPausing() == true) {

            currentTown = town;

            if (weatherThread.isPausing() == true ) {

                weatherThread.start();
                System.out.println("BUTTON P TRUE");

            } else {
                weatherThread.removeObserver(weatherDisplay);
                weatherDisplay = new WeatherDisplay(town, weatherList, timeListChart);
                weatherThread.addObserver(weatherDisplay);
                weatherThread.start();
                clearChartsSeries();

                System.out.println("BUTTON P FALSE");
            }
        } else {
            System.out.println("MIASTO JEST OBSŁUGIWANE");
        }
    }

    /**
     *
     * Set images of weather
     *
     */


    public void setImage() {

        image = imageChooser.getImageChoose();
        imageLabel.setImage(image);

    }

    /**
     *
     * Creates a JSON file based on a table that collects weather data, time and Town.
     *
     * @param actionEvent
     */

    public void pressSaveBtn(ActionEvent actionEvent) {

        System.out.println("WWW " + weatherList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save weather application data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File jsonFile = fileChooser.showSaveDialog(timeLabel.getScene().getWindow());

        String town = userTown.getText().replace(" ", "_");

        Map<String, ArrayList<Object>> map = new HashMap<>();
        map.put("Weather", new ArrayList<>(weatherList));
        map.put("Time", new ArrayList<>(timeListChart));
        map.put("Town", new ArrayList<>(Collections.singleton(town)));

        if (jsonFile != null) {
            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                gson.toJson(map, fileWriter);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     *
     *  Opens a JSON file and fills lists by weather data, time and Town.
     *
     * @param actionEvent
     */

    public void pressOpenBtn(ActionEvent actionEvent) {


        if (weatherThread.isRunning() == false) {

            Map openData = null;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open weather application data");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showOpenDialog(timeLabel.getScene().getWindow());

            if (file != null) {
                try (InputStream inputStream = new FileInputStream(file)) {
                    Reader reader = new InputStreamReader(inputStream);
                    openData = gson.fromJson(reader, Map.class);

                } catch (IOException e) {
                    System.out.println("Nie udało się załadować pliku");
                }

                weatherArray = gson.fromJson(openData.get("Weather").toString(), new TypeToken<ArrayList<Weather>>() {
                }.getType());
                String[] time = gson.fromJson(openData.get("Time").toString(), String[].class);
                String[] town = gson.fromJson(openData.get("Town").toString(), String[].class);

                weatherList.clear();
                weatherList.addAll(weatherArray);

                timeListChart.clear();
                timeListChart.addAll(Arrays.asList(time));

                userTown.setText(town[0]);
                System.out.println("OTWARTO PLIK");
            }


        } else {

            System.out.println("APLIKACJA MUSI BYC WYŁĄCZONA");

        }
    }

    /**
     *
     * Reset all values of statistic
     *
     */



    public void resetStatistic() {

        statistic = new Statistic(0, 0, 0, 0, 0, 0, 0, 0, 0);
        statisticsList.add(statisticsList.size(), statistic);
        clearChartsSeries();

        for (int i = 0; i < statisticsList.size() - 1; i++) {
            statisticsList.remove(i);

        }
    }

    /**
     *
     * Clear all charts series.
     *
     */

    public void clearChartsSeries() {
        timeListChart.clear();
        weatherList.clear();
        tempSeries.getData().clear();
        humiSeries.getData().clear();
        pressSeries.getData().clear();

    }

    /**
     * Open new window of statistic and passes there statistic list.
     *
     *
     * @param actionEvent
     * @throws IOException
     */

    public void pressStatisticButton(ActionEvent actionEvent) throws IOException {

        try {
            if(weatherThread.isRunning() == false && weatherThread.isPausing() == false){
                resetStatistic();
            }

            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("statisticSample.fxml"));

            Parent root1 = fxmlLoader1.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(true);

            StatisticController statisticController = fxmlLoader1.getController();
            statisticController.setStatistic(statisticsList);

            statisticsList.addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {

                    statisticController.setStatistic(statisticsList);
                }
            });

            root1.setOnMousePressed(event -> {

                x = event.getSceneX();
                y = event.getSceneY();
            });

            root1.setOnMouseDragged(event -> {

                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });


            stage.showAndWait();
        } catch (RuntimeException ex) {
            System.out.println("Aplikacja nie jest uruchomiona");
        }

    }
}