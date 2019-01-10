package sample.controller;


/**
 * Class creates a statistic object
 */
public class Statistic {

    private int measurement;
    private double meanTempValue;
    private double meanPressureValue;
    private double stdTempValue;
    private double stdPressureValue;
    private double meanHumidityValue;
    private double stdHumidityValue;
    private double tempMin;
    private double tempMax;

    public Statistic(int measurement, double meanTempValue, double meanPressureValue, double stdTempValue, double stdPressureValue, double meanHumidityValue, double stdHumidityValue,double tempMax, double tempMin) {
        this.measurement = measurement;
        this.meanTempValue = meanTempValue;
        this.meanPressureValue = meanPressureValue;
        this.stdTempValue = stdTempValue;
        this.stdPressureValue = stdPressureValue;
        this.meanHumidityValue = meanHumidityValue;
        this.stdHumidityValue = stdHumidityValue;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    /**
     *The method returns the number of measurements of the statistics object
     * @return measurement
     */
    public int getMeasurement() {
        return measurement;
    }
    /**
     *The method returns the mean of temperature of the statistics object
     * @return meanTempValue
     */

    public double getMeanTempValue() {
        return meanTempValue;
    }

    /**
     *The method returns the mean of pressure of the statistics object
     * @return meanPressValue
     */

    public double getMeanPressureValue() {
        return meanPressureValue;
    }


    /**
     *The method returns the standard deviation of temperature of the statistics object
     * @return stdTempValue
     */
    public double getStdTempValue() {
        return stdTempValue;
    }


    /**
     *The method returns the standard deviation of pressure of the statistics object
     * @return stdPressureValue
     */
    public double getStdPressureValue() {
        return stdPressureValue;
    }

    /**
     *The method returns the mean of humidity of the statistics object
     * @return meanHumidityValue
     */
    public double getMeanHumidityValue() {
        return meanHumidityValue;
    }


    /**
     *The method returns the standard deviation of humidity of the statistics object
     * @return stdHumidityValue
     */
    public double getStdHumidityValue() {
        return stdHumidityValue;
    }


    /**
     *The method returns the minimum temperature of the statistics object
     * @return tempMin
     */
    public double getTempMin() {
        return tempMin;
    }
    /**
     *The method returns the maksimum temperature of the statistics object
     * @return tempMax
     */
    public double getTempMax() {
        return tempMax;
    }

    @Override
    public String toString() {
        return "measurement = " + measurement + "\n" +
                "meanTempValue = " + meanTempValue + "\n" +
                "meanPressureValue = " + meanPressureValue + "\n" +
                "meanHumidityValue = " + meanHumidityValue + "\n" +
                "stdTempValue = " + stdTempValue + "\n" +
                "stdPressureValue = " + stdPressureValue + "\n" +
                "stdHumidityValue = " + stdHumidityValue;
    }
}
