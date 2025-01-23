package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.QuadraticFunction;
import drevo.stochastic.annealing.monitoring.AnnealingListener;
import drevo.stochastic.annealing.monitoring.AnnealingState;

class UnimodalFunctionsTest extends BaseFunctionTest {
    @Test
    void minimizeQuadraticFunctionDefaultTest() {
        // Define a simple Function for testing
        QuadraticFunction function = new QuadraticFunction();

        // Run Simulated Annealing
        QuadraticFunction result = (QuadraticFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x()) < 10e-2, String.format("The x value do not minimize (x - 2)^2. expectedX: %.5f, result.x: %.5f", expectedX, result.x()));
    }

    @Test
    void minimizeQuadraticFunctionTest() {
        // Define a simple Function for testing
        QuadraticFunction function = new QuadraticFunction();

        // Run Simulated Annealing
        QuadraticFunction result = (QuadraticFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x()) < 10e-2, String.format("The x value do not minimize (x - 2)^2. expectedX: %.5f, result.x: %.5f", expectedX, result.x()));
    }
}
