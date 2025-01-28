package drevo.math;

import java.util.concurrent.ThreadLocalRandom;

public class Interval {
    private final double min;
    private final double max;
    private double value;

    public Interval(double min, double max, double value) {
        this.min = min;
        this.max = max;

        value(value); // Validate and set the value
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public double value() {
        return value;
    }

    public void value(double value) {
        this.value = value;

        validateValue();
    }

    private void validateValue() {
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }
    }

    public Interval change() {
        value = ThreadLocalRandom.current().nextDouble() * (max - min) + min;

        return this;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "min=" + min +
                ", max=" + max +
                ", value=" + value +
                '}';
    }
}