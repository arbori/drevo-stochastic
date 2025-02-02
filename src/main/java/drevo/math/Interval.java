package drevo.math;

import java.util.concurrent.ThreadLocalRandom;

public class Interval {
    private final double low;
    private final double upper;
    private double value;

    // Constructor
    public Interval(double low, double upper, double value) {
        if (low > upper) {
            throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
        }

        this.low = low;
        this.upper = upper;
        
        value(value); // Validate and set the initial value
    }

    public Interval(Interval other) {
        if (other.low > other.upper) {
            throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
        }
        
        this.low = other.low;
        this.upper = other.upper;
        
        value(other.value); // Validate and set the initial value
    }

    // Getters for low and upper (read-only)
    public double low() {
        return low;
    }

    public double upper() {
        return upper;
    }

    // Getter and setter for value
    public double value() {
        return value;
    }

    public void value(double value) {
        if (value < low) {
            this.value = low;
        } else if(value > this.upper) {
            this.value = this.upper;
        } else {
            this.value = value;
        }
    }

    public Interval change() {
        value(ThreadLocalRandom.current().nextDouble() * (upper - low) + low);

        return this;
    }

    public Interval change(double variation) {
        value(value *(1 + variation));

        return this;
    }

    // Interval arithmetic operations
    public Interval add(Interval other) {
        double newLow = this.low + other.low;
        double newUpper = this.upper + other.upper;
        double newValue = this.value + other.value;
        return new Interval(newLow, newUpper, newValue);
    }

    public Interval subtract(Interval other) {
        double newLow = this.low - other.upper;
        double newUpper = this.upper - other.low;
        double newValue = this.value - other.value;
        return new Interval(newLow, newUpper, newValue);
    }

    public Interval multiply(Interval other) {
        double[] products = {
            this.low * other.low,
            this.low * other.upper,
            this.upper * other.low,
            this.upper * other.upper
        };

        double newLow = Math.min(Math.min(products[0], products[1]), Math.min(products[2], products[3]));
        double newUpper = Math.max(Math.max(products[0], products[1]), Math.max(products[2], products[3]));
        double newValue = this.value * other.value;
        
        return new Interval(newLow, newUpper, newValue);
    }

    public Interval divide(Interval other) {
        if (other.low <= 0 && other.upper >= 0) {
            throw new ArithmeticException("Division by an interval containing zero is undefined.");
        }
        
        double[] quotients = {
            this.low / other.low,
            this.low / other.upper,
            this.upper / other.low,
            this.upper / other.upper
        };
        
        double newLow = Math.min(Math.min(quotients[0], quotients[1]), Math.min(quotients[2], quotients[3]));
        double newUpper = Math.max(Math.max(quotients[0], quotients[1]), Math.max(quotients[2], quotients[3]));
        double newValue = this.value / other.value;
        
        return new Interval(newLow, newUpper, newValue);
    }

    @Override
    public String toString() {
        return "Interval[low=" + low + ", upper=" + upper + ", value=" + value + "]";
    }
}
