package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.MichalewiczFunction;

class MichalewiczFunctionTest extends BaseFunctionTest {
    @Test
    void maximizeMichalewiczFunctionDefaultTest() {
        // Define the Michalewicz Function for testing
        MichalewiczFunction function = new MichalewiczFunction();

        // Run Simulated Annealing with the default context
        MichalewiczFunction result = (MichalewiczFunction) SimulatedAnnealing.optimize(
                maximizeDefaultAnnealingContext,
                function,
                handler);

        double expectedValueThreshold = -1.0; // Known approximate maximum value for 5D Michalewicz function
        double computedValue = result.compute(); // Negated since the function is inverted for minimization

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
                handler);

        double expectedValueThreshold = -1.0; // Known approximate maximum value for 5D Michalewicz function
        double computedValue = result.compute(); // Negated since the function is inverted for minimization

        // Assert that we found a reasonable solution
        assertTrue(computedValue >= expectedValueThreshold,
                String.format("Optimization failed. Expected value >= %.5f, but got %.5f.", expectedValueThreshold,
                        computedValue));
    }
}
