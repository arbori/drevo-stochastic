package drevo.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class IntervalTest {
    double minValue = 3;
    double maxValue = 5;
    double value = 4;

    @Test
    void setMaxValueTest() {
        Interval interval = new Interval(minValue, maxValue, value);
        
        double newValue = maxValue;

        interval.value(newValue);

        assertEquals(newValue, interval.max());
    }

    @Test
    void setMinValueTest() {
        Interval interval = new Interval(minValue, maxValue, value);
        
        double newValue = minValue;

        interval.value(newValue);

        assertEquals(newValue, interval.min());
    }

    @Test
    void setValueAdjustToMinTest() {
        Interval interval = new Interval(minValue, maxValue, value);
        
        double newValue = minValue - ThreadLocalRandom.current().nextDouble();

        interval.value(newValue);

        assertEquals(minValue, interval.value());
    }

    @Test
    void setValueAdjustToMaxTest() {
        Interval interval = new Interval(minValue, maxValue, value);
        
        double newValue = maxValue + ThreadLocalRandom.current().nextDouble();

        interval.value(newValue);

        assertEquals(maxValue, interval.value());
    }

    @Test
    void changeValueTest() {
        Interval interval = new Interval(minValue, maxValue, value);
        
        double newValue = interval.change().value();

        assertTrue(interval.min() <= newValue && newValue <= interval.max());
    }
}
