package javaexternal.multithreading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class BusesMain {
    private static Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        // Get stops data from a file
        String filename = "src/main/resources/bus_stops_data.csv";
        Map<String, Integer> stopsDataMap = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                stopsDataMap.put(data[0], Integer.parseInt(data[1]));
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        // Create a list of stops
        List<BusStop> route = new ArrayList<>();
        BusStop stop;
        for (Map.Entry<String, Integer> entry: stopsDataMap.entrySet()) {
            stop = new BusStop(entry.getKey(), entry.getValue());
            route.add(stop);
        }

        // Run buses

        int minInterval = 100;
        int maxInterval = 200;
        int numOfIterations = 2;
        int nBuses = 4;
        System.out.println(nBuses + " buses are working...");
        for (int i = 0; i < nBuses; i++) {
            new Bus(i+1, route, numOfIterations);
            try {
                sleep((int) ((minInterval + maxInterval) / 2 * Math.random()));
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

}
