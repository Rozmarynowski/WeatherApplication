package sample.clock;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AtomicClock implements Runnable, Observable {

    private Thread worker;
    protected volatile boolean isRunning = false;
    private int interval;
    private volatile ArrayList<Observer> observersList = new ArrayList<>();



    public AtomicClock() {
        interval = 1000;
    }

    public void start() {
        worker = new Thread(this, " Clock thread");
        worker.start();
    }


    @Override
    public void addObservere(Observer observer) {
        if (!observersList.contains(observer)) {
            observersList.add(observer);
        }

    }
    @Override
    public void removeObserver(Observer observer) {

        if (observersList.contains(observer)) observersList.remove(observer);

    }


    @Override
    public void updateObservere() {
        for (Observer o : observersList) {
            LocalTime time = LocalTime.now();
            LocalDate date = LocalDate.now();
            o.updateTime(time,date);

        }

    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {

            try {
                Thread.sleep(interval);
                updateObservere();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }
        }
    }
}
