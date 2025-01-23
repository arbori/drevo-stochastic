package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.StepFunction;
import drevo.stochastic.annealing.monitoring.AnnealingListener;
import drevo.stochastic.annealing.monitoring.AnnealingState;

class StepFunctionTest extends BaseFunctionTest {
    @Test
    void minimizeStepFunctionDefaultTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedX = 2.0;
        double expected  = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() > expectedX, String.format("The x value do not minimize step function: expectedX: %.5f, result.x(): %.5f", expectedX, result.x()));
    }

    @Test
    void minimizeStepFunctionTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedX = 2.0;
        double expected  = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() > expectedX, String.format("The x value do not minimize step function: expectedX: %.5f, result.x(): %.5f", expectedX, result.x()));
    }

    @Test
    void maximizeStepFunctionDefaultTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            maximizeDefaultAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedX = 1.0;
        double expected  = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() < expectedX, String.format("The x value do not minimize step function: expected: %.5f, result: %.5f", expectedX, result.x()));
    }

    @Test
    void maximizeStepFunctionTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            maximizeAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedX = 1.0;
        double expected  = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() < expectedX, String.format("The x value do not minimize step function: expectedX: %.5f, result: %.5f", expectedX, result.x()));
    }
}
