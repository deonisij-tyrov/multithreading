package javaexternal.multithreading;

import org.junit.Test;

import static org.junit.Assert.*;

public class BusStopTest {
    @Test
    public void equalsIfNameAndMaxBusesAreEqual() {
        BusStop stop1 = new BusStop(new String("Stop A"), 2);
        BusStop stop2 = new BusStop("Stop A", 2);
        assertEquals(stop1, stop2);
    }

    @Test
    public void notEqualIfNamesAreNotEqual() {
        BusStop stop1 = new BusStop("Stop A", 2);
        BusStop stop2 = new BusStop("Stop B", 2);
        assertNotEquals(stop1, stop2);
    }

    @Test
    public void notEqualIfMaxBusesAreNotEqual() {
        BusStop stop1 = new BusStop("Stop A", 2);
        BusStop stop2 = new BusStop("Stop A", 3);
        assertNotEquals(stop1, stop2);
    }

    @Test
    public void sameHashIfEqual() {
        BusStop stop1 = new BusStop("Stop A", 2);
        BusStop stop2 = new BusStop("Stop A", 2);
        assertEquals(stop1.hashCode(), stop2.hashCode());
    }
}
