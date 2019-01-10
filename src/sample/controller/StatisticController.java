package sample.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *The class is responsible for displaying current statistical data
 */
public class StatisticController implements Initializable {

    private ObservableList<Statistic> statisticList;
    private Statistic statistic2;


    @FXML
    private Label tempMaxLabel;

    @FXML
    private Label tempMinLabel;

    @FXML
    private Button statisticClose;

    @FXML
    private Label meanTemperature;

    @FXML
    private Label meanPressure;

    @FXML
    private Label meanHumidity;

    @FXML
    private Label stdTemperature;

    @FXML
    private Label stdPressure;

    @FXML
    private Label stdHumidity;

    @FXML
    private Label measurementLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     *The method gets a current observable list with statistical data
     * @param statisticList
     */
    public void setStatistic(ObservableList<Statistic> statisticList) {
        this.statisticList = statisticList;
        statistic2 = statisticList.get(statisticList.size() - 1);
        System.out.println(statistic2);
        updateStatistic();
        System.out.println("STATYSTYKI - AKTUALIZACJA");


    }
    /**
     * This method close window (actual controller)
     * @param actionEvent
     */

    public void closeBtn(ActionEvent actionEvent) {

        Stage stage2 = (Stage) statisticClose.getScene().getWindow();
        stage2.close();


    }

    /**
     * The method sets the current statistical data in the window
     */
    public void updateStatistic() {


        measurementLabel.textProperty().set(String.valueOf(statistic2.getMeasurement()));
        meanTemperature.textProperty().set(String.valueOf(statistic2.getMeanTempValue() + " \u00b0C"));
        meanHumidity.textProperty().set(String.valueOf(statistic2.getMeanHumidityValue() + " %"));
        meanPressure.textProperty().set(String.valueOf(statistic2.getMeanPressureValue() + " hPa"));
        stdTemperature.textProperty().set(String.valueOf(statistic2.getStdTempValue() + " \u00b0C"));
        stdPressure.textProperty().set(String.valueOf(statistic2.getStdPressureValue() + " hPa"));
        stdHumidity.textProperty().set(String.valueOf(statistic2.getStdHumidityValue() + " %"));
        tempMaxLabel.textProperty().set(String.valueOf(statistic2.getTempMax() + " \u00b0C"));
        tempMinLabel.textProperty().set(String.valueOf(statistic2.getTempMin() + " \u00b0C"));


    }


}


