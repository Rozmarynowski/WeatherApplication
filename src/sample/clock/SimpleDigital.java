package sample.clock;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;



public class SimpleDigital implements Observer, DisplayTime {


    private int hour;
    private int minute;
    private String clockName;
    private String dateName;
    private ObservableList<LocalTime> timeList;
    private ObservableList<LocalDate> dateList;

    public SimpleDigital(String clockName,String dateName, ObservableList<LocalTime> timeList,ObservableList<LocalDate> dateList) {
        this.clockName = clockName;
        this.dateName = dateName;
        this.timeList = timeList;
        this.dateList = dateList;
    }




    @Override
    public void updateTime(LocalTime time, LocalDate date) {

        timeList.add(time);
        dateList.add(date);

    }

    @Override
    public void display() {


    }
}
