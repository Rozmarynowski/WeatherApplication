package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * class that creates line graphs.
 */

public class ChartController implements Initializable {

    XYChart.Series<String, Number> tempSeries;
    XYChart.Series<String, Number> humiSeries;
    XYChart.Series<String, Number> pressSeries;


    @FXML
    private Button tempBtn;

    @FXML
    private Button PressBtn;

    @FXML
    private Button humiBtn;

    @FXML
    private CategoryAxis yAxis;

    @FXML
    private NumberAxis xAxis;


    @FXML
    private Button chartClose;

    @FXML
    private LineChart<String, Number> chartWindow;

    /**
     * This method close window (actual controller)
     * @param actionEvent
     */

    public void closeBtn(javafx.event.ActionEvent actionEvent) {

        // get a handle to the stage
        Stage stage = (Stage) chartClose.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }



    public void setChart(XYChart.Series<String, Number> tempSeries, XYChart.Series<String, Number> humiSeries, XYChart.Series<String, Number> pressSeries) {
        this.tempSeries = tempSeries;
        this.humiSeries = humiSeries;
        this.pressSeries = pressSeries;






        setTempSeries();

    }

    /**
     *
     * This method draws a graph of temperature dependence from time.
     * @param actionEvent
     */

    public void pressTempBtn(javafx.event.ActionEvent actionEvent) {

        setTempSeries();
    }

    /**
     *This method draws a graph of pressure dependence from time.
     * @param actionEvent
     */
    public void pressPressBtn(javafx.event.ActionEvent actionEvent) {

        xAxis.setLabel("Pressure [hPa]");
        chartWindow.getData().clear();
        chartWindow.getData().addAll(pressSeries);
        chartSett();

    }

    /**
     *This method draws a graph of humidity dependence from time.
     * @param actionEvent
     */
    public void pressHumiBtn(javafx.event.ActionEvent actionEvent) {

        xAxis.setLabel("Humidity [%]");
        chartWindow.getData().clear();
        chartWindow.getData().addAll(humiSeries);
        chartSett();

    }

    /**
     * This method set basic features of the chart
     */
    public void chartSett() {

        chartWindow.setAnimated(false);
        chartWindow.setLegendVisible(false);
        yAxis.setLabel("Time [HH:mm]");

        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);

    }

    /**
     *This method set temperature dependence from time series.
     */

    public void setTempSeries() {

        xAxis.setLabel("Temperature [\u00b0C]");

        chartWindow.getData().clear();
        chartWindow.getData().addAll(tempSeries);

        chartSett();

    }


}
