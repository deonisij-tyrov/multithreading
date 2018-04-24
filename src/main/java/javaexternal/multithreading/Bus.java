package javaexternal.multithreading;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Bus implements Runnable {
    private int id;
    private LinkedList<BusStop> route;
    private Semaphore currentSemaphore;
    private BusStop targetStop;
    private int iterationCount = 0;
    private final BusStop lastStop;
    private int numOfIterations;

    public Bus(int num, Collection<BusStop> route, int numOfIterations) {
        this.id = num;
        this.route = new LinkedList<>(route);
        lastStop = this.route.peekLast();
        this.numOfIterations = numOfIterations;
        new Thread(this, "Bus " + 1).start();
    }

    @Override
    public void run() {
        while (route.size() > 0 && iterationCount < numOfIterations) {
            targetStop = route.pollFirst();
            route.addLast(targetStop);
            if (targetStop.equals(lastStop)) {
                iterationCount++;
            }
            move();

            // Stop when there is a free place
            try {
                currentSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();

            // Leave and release a place
            leave();
            currentSemaphore.release();
        }
    }

    public void move() {
        currentSemaphore = targetStop.getSemaphore();
        System.out.println(this + " moved to " + targetStop);
        try {
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this + " arrived to " + targetStop);
    }

    public void stop() {
        System.out.println(this + " stopped at " + targetStop);
        targetStop.addBus(this);
        System.out.println("Buses at " + targetStop + ": " + targetStop.getBuses());
        try {
            sleep(targetStop.getMaxBuses() * 50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void leave() {
        targetStop.removeBus(this);
        System.out.println(this + " leaved " + targetStop);
        System.out.println("Buses at " + targetStop + ": " + targetStop.getBuses());
    }

    @Override
    public String toString() {
        return "Bus " + id;
    }
}
