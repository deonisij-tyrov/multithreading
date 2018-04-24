package javaexternal.multithreading;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static org.mockito.Mockito.*;

public class BusTest {
    // Class to be tested
    private Bus bus;

    // Dependencies
    private BusStop stop;
    private List<BusStop> route = new ArrayList<>();

    @Before
    public void setup() {
        stop = mock(BusStop.class);
        route.add(stop);
    }

    @Test
    public void cannotStopIfNoPlaces() {
        when(stop.getSemaphore()).thenReturn(new Semaphore(0));
        bus = new Bus(1, route, 1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(stop, times(0)).addBus(bus);
    }

    @Test
    public void stopIfThereIsAPlace() {
        when(stop.getSemaphore()).thenReturn(new Semaphore(1));
        bus = new Bus(1, route, 1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(stop).addBus(any(Bus.class));
    }

    @Test
    public void addBusWhenStoppedAndRemoveWhenLeft() {
        when(stop.getSemaphore()).thenReturn(new Semaphore(1));
        ArgumentCaptor<Bus> captor = ArgumentCaptor.forClass(Bus.class);
        bus = new Bus(1, route, 1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(stop).addBus(captor.capture());
        Bus added = captor.getValue();
        assertEquals("This bus should be added to the stop's list",
                bus, added);

        verify(stop).removeBus(captor.capture());
        Bus removed = captor.getValue();
        assertEquals("This bus should be removed from the stop's list",
                bus, removed);
    }

    @Test
    public void performTwoIterations() {
        when(stop.getSemaphore()).thenReturn(new Semaphore(1));
        bus = new Bus(1, route, 2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verify(stop, times(2)).getSemaphore();
    }
}
