package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.PlateauFunction;

class PlateauFunctionTest extends BaseFunctionTest {
    @Test
    void minimizePlateauFunctionDefaultTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedX = 1.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, resultY));
        assertTrue(Math.abs(result.x()) < expectedX, String.format("The x value do not minimize step function: expectedX: %f, result.x: %f", expectedX, result.x()));
    }

    @Test
    void minimizePlateauFunctionTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            handler);

        double expectedX = 1.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, resultY));
        assertTrue(Math.abs(result.x()) < expectedX, String.format("The x value do not minimize step function: expectedX: %f, result.x: %f", expectedX, result.x()));
    }

    @Test
    void maximizePlateauFunctionDefaultTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(
            maximizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedX = 1.0;
        double expected  = 9.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the maximum: expected: %f, result: %f", expected, resultY));
        assertTrue(expectedX <= Math.abs(result.x()), String.format("The x value do not maximize step function: expectedX: %f, result.x: %f", expectedX, result.x()));
    }

    @Test
    void maximizePlateauFunctionTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(
            maximizeAnnealingContext, 
            function,
            handler);

        double expectedX = 1.0;
        double expected  = 9.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the maximum: expected: %f, result: %f", expected, resultY));
        assertTrue(expectedX <= Math.abs(result.x()), String.format("The x value do not maximize step function: expectedX: %f, result.x: %f", expectedX, result.x()));
    }
}
