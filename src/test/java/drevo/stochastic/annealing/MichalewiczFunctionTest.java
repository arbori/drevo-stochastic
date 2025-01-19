package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.monitoring.AnnealingListener;
import drevo.stochastic.annealing.monitoring.AnnealingState;

class MichalewiczFunctionTest extends BaseFunctionTest {
    class MichalewiczFunction implements AnnealingFunction {
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
            return -result; // Negate because the algorithm minimizes by default
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

    @Test
    void maximizeMichalewiczFunctionDefaultTest() {
        // Define the Michalewicz Function for testing
        MichalewiczFunction function = new MichalewiczFunction();

        // Run Simulated Annealing with the default context
        MichalewiczFunction result = (MichalewiczFunction) SimulatedAnnealing.optimize(
            maximizeDefaultAnnealingContext,
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedValueThreshold = -1.0; // Known approximate maximum value for 5D Michalewicz function
        double computedValue = -result.compute(); // Negated since the function is inverted for minimization

        // Assert that we found a reasonable solution
        assertTrue(computedValue >= expectedValueThreshold,
                String.format("Optimization failed. Expected value >= %.5f, but got %.5f.", expectedValueThreshold,
                        computedValue));
    }

    @Test
    void maximizeMichalewiczFunctionTest() {
        // Define the Michalewicz Function for testing
        MichalewiczFunction function = new MichalewiczFunction();

        // Run Simulated Annealing with a custom context
        MichalewiczFunction result = (MichalewiczFunction) SimulatedAnnealing.optimize(
            maximizeAnnealingContext,
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedValueThreshold = -1.0; // Known approximate maximum value for 5D Michalewicz function
        double computedValue = -result.compute(); // Negated since the function is inverted for minimization

        // Assert that we found a reasonable solution
        assertTrue(computedValue >= expectedValueThreshold,
                String.format("Optimization failed. Expected value >= %.5f, but got %.5f.", expectedValueThreshold,
                        computedValue));
    }
}
