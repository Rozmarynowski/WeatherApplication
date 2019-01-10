package sample.Weather;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Class that represents thread where weather is updating.
 * Updating observers which are added to this thread.
 */

public class WeatherThread implements Runnable, Observable {

    private Thread thread;
    private int interval;
    private volatile boolean isRunning = false;
    private volatile boolean isPausing = false;
    private volatile ArrayList<Observer> observerList = new ArrayList<>();

    /**
     * Set interval of thread.
     */
    public WeatherThread() {
        interval = 60001;
    }

    /**
     * Set interval of thread.
     */

    public WeatherThread(int interval) {
        this.interval = interval;

    }

    /**
     *
     * Get interval of weather
     * @return interval time
     */
    public int getInterval() {
        return interval;
    }


    /**
     *
     *  Add new observer to observerList
     * @param observer
     */

    @Override
    public void addObserver(Observer observer) {
        if (!observerList.contains(observer)) {
            observerList.add(observer);
        }
        System.out.println("Obserwerzy " + observerList.size());

    }


    /**
     * Remove existing observer.
     * @param observer
     */

    @Override
    public void removeObserver(Observer observer) {
        if (observerList.contains(observer)) {
            observerList.remove(observer);
            isRunning = false;
        }
        System.out.println("Obserwerzy remove " + observerList.size());

    }

    @Override
    public void updateObservers() throws IOException {
        for (Observer o : observerList) {
            Weather weather = WeatherStation.getWeatherFromCity(o.getCity());
            o.updateWeather(weather);
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPausing() {
        return isPausing;
    }


    /**
     * Pauses weather thread
     */
    public void clickPause() {
        pause();
    }

    /**
     * Starts new thread or resume if it is paused
     *
     */
    public void start() {

        if (isPausing == false) {
            thread = new Thread(this, "Weather Thread");
            thread.start();
            System.out.println("WYSTARTOWANO");
        } else {
            resume();
            System.out.println("WZNOWIONO");
        }

    }

    /**
     * Resume paused thread
     *
     */
    synchronized void resume() {

        notify();
        isPausing = false;
        System.out.println("WZNOWIONO");

    }

    /**
     * Stops thread
     */

    public void stop() {
        isRunning = false;
        thread.interrupt();
    }

    /**
     * Paused thread
     */
    synchronized void pause() {
        isPausing = true;
    }

    /**
     * Method is called when thread start.
     */


    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            try {
                updateObservers();
                System.out.println("UPDATE");
                Thread.sleep(interval);
                synchronized (this) {
                    while (isPausing) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed");
            } catch (IOException e) {
                System.out.println("NIE PODANO MIASTA LUB WPROWADZONO ZŁĄ NAZWĘ");
                isRunning = false;

                return;
            }

        }

    }


}
