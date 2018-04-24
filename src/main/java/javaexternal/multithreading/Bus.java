package javaexternal.multithreading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static Logger logger = LogManager.getRootLogger();

    public Bus(int num, Collection<BusStop> route, int numOfIterations) {
        this.id = num;
        this.route = new LinkedList<>(route);
        lastStop = this.route.peekLast();
        this.numOfIterations = numOfIterations;
        new Thread(this, "Bus " + num).start();
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
                logger.error(e.getMessage());
            }
            stop();

            // Leave and release a place
            leave();
            currentSemaphore.release();
        }
    }

    public void move() {
        currentSemaphore = targetStop.getSemaphore();
        logger.info(this + " moved to " + targetStop);
        try {
            sleep(300);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        logger.info(this + " arrived to " + targetStop);
    }

    public void stop() {
        logger.info(this + " stopped at " + targetStop);
        targetStop.addBus(this);
        logger.info("Buses at " + targetStop + ": " + targetStop.getBuses());
        try {
            sleep(targetStop.getMaxBuses() * 50);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public void leave() {
        targetStop.removeBus(this);
        logger.info(this + " leaved " + targetStop);
        logger.info("Buses at " + targetStop + ": " + targetStop.getBuses());
    }

    @Override
    public String toString() {
        return "Bus " + id;
    }
}
