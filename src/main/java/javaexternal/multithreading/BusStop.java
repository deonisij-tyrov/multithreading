package javaexternal.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class BusStop {
    private String name;
    private Semaphore semaphore;
    private int maxBuses;
    private List<Bus> buses;

    public BusStop(String name, int maxBuses) {
        this.name = name;
        this.maxBuses = maxBuses;
        this.semaphore = new Semaphore(maxBuses, true);
        buses = new ArrayList<>();
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void addBus(Bus bus) {
        buses.add(bus);
    }

    public void removeBus(Bus bus) {
        buses.remove(bus);
    }

    public int getMaxBuses() {
        return maxBuses;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusStop busStop = (BusStop) o;
        return maxBuses == busStop.maxBuses &&
                Objects.equals(name, busStop.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxBuses);
    }
}
