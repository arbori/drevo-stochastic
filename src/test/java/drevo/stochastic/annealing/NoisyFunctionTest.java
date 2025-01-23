package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.QuadraticNoiseFunction;
import drevo.stochastic.annealing.monitoring.AnnealingListener;
import drevo.stochastic.annealing.monitoring.AnnealingState;

class NoisyFunctionTest extends BaseFunctionTest {
    @Test
    void minimizeQuadraticNoiseFunctionDefaultTest() {
        // Define a simple Function for testing
        QuadraticNoiseFunction function = new QuadraticNoiseFunction();

        // Run Simulated Annealing
        QuadraticNoiseFunction result = (QuadraticNoiseFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(expectedMin < resultY && resultY < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, resultY));
        assertTrue(expectedMinX < result.x() && result.x() < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, result.x()));
    }

    @Test
    void minimizeQuadraticNoiseFunctionTest() {
        // Define a simple Function for testing
        QuadraticNoiseFunction function = new QuadraticNoiseFunction();

        // Run Simulated Annealing
        QuadraticNoiseFunction result = (QuadraticNoiseFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            new AnnealingListener() {
                protected void handleStateChange(AnnealingState state) {
                    System.out.println(state);
                }
            });

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(expectedMin < resultY && resultY < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, result.x()));
        assertTrue(expectedMinX < result.x() && result.x() < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, result.x()));
    }
}
