package sample.clock;

import java.time.LocalDate;
import java.time.LocalTime;

public interface Observer {
    void updateTime(LocalTime time, LocalDate date);
}
