package javaexternal.multithreading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class BusesMain {
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
            e.printStackTrace();
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
        for (int i = 0; i < 4; i++) {
            new Bus(i+1, route, numOfIterations);
            try {
                sleep((int) ((minInterval + maxInterval) / 2 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
