/*
 * Copyright (C) 2024 Marcelo Arbori Nogueira - marcelo.arbori@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package drevo.math;

import java.util.concurrent.ThreadLocalRandom;

public class Interval {
    private final double low;
    private final double upper;
    private double value;

    // Constructor
    public Interval(double low, double upper) {
        isValidBounds(low, upper);

        this.low = low;
        this.upper = upper;
         
        value(ThreadLocalRandom.current().nextDouble()*(upper - low) + low); // Validate and set the initial value
    }

    public Interval(double low, double upper, double value) {
        isValidBounds(low, upper);

        this.low = low;
        this.upper = upper;
        
        value(value); // Validate and set the initial value
    }

    public Interval(Interval other) {
        isValidBounds(other.low, other.upper);
        
        this.low = other.low;
        this.upper = other.upper;
        
        value(other.value); // Validate and set the initial value
    }

    private void isValidBounds(double low, double upper) {
        if (low > upper) {
            throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
        }
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
        value(this.value * (1 + variation));

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
