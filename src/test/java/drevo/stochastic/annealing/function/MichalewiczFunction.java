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
package drevo.stochastic.annealing.function;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class MichalewiczFunction implements AnnealingFunction {
    private static final int DIMENSIONS = 5; // Number of dimensions
    private static final double PI = Math.PI;
    private static final double M = 10; // Steepness parameter
    private double[] x = new double[DIMENSIONS]; // Current state (solution candidate)

    // Constructor: initializes with a random valid state
    public MichalewiczFunction() {
        randomize();
    }

    @Override
    public double compute() {
        // Compute the Michalewicz function value
        double result = 0.0;
        for (int i = 0; i < DIMENSIONS; i++) {
            double xi = x[i];
            result += Math.sin(xi) * Math.pow(Math.sin((i + 1) * xi * xi / PI), 2 * M);
        }
        return result;
    }

    @Override
    public void reconfigure() {
        // Modify the state slightly to explore the domain
        int randomIndex = ThreadLocalRandom.current().nextInt(DIMENSIONS);
        double perturbation = ThreadLocalRandom.current().nextDouble(-0.1, 0.1); // Small change
        x[randomIndex] += perturbation;

        // Ensure the new state is valid
        if (!isValid()) {
            x[randomIndex] -= perturbation; // Revert if out of bounds
        }
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof MichalewiczFunction) {
            this.x = Arrays.copyOf(((MichalewiczFunction) f).x, DIMENSIONS);
        }
    }

    @Override
    public boolean isValid() {
        // Check if all dimensions are within the valid range [0, PI]
        for (double xi : x) {
            if (xi < 0.0 || xi > PI) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AnnealingFunction copy() {
        MichalewiczFunction clone = new MichalewiczFunction();
        clone.x = Arrays.copyOf(this.x, DIMENSIONS);
        return clone;
    }

    // Helper method to initialize with random values in [0, PI]
    private void randomize() {
        for (int i = 0; i < DIMENSIONS; i++) {
            x[i] = ThreadLocalRandom.current().nextDouble(0, PI);
        }
    }

    @Override
    public String toString() {
        return "MichalewiczFunction{" +
                "x=" + Arrays.toString(x) +
                ", value=" + compute() +
                '}';
    }
}
