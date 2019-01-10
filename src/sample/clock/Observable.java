package sample.clock;

public interface Observable {
    void addObservere(Observer observer);
    void removeObserver(Observer observer);
    void updateObservere();
}
