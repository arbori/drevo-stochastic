package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.RastriginFunction;
import drevo.stochastic.annealing.function.SphereFunction;

class HighDimensionalFunctionTest extends BaseFunctionTest {
    @Test
    void minimizeSphereFunctionDefaultTest() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int dimention = 20 + rnd.nextInt(30); // Higher number between 20 and 50

        // Define a simple Function for testing
        SphereFunction function = new SphereFunction(dimention);

        // Run Simulated Annealing
        SphereFunction result = (SphereFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x(i)) < 10e-2, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x(i)));
        }

        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum. Expected: %.5f, result: %.5f", expected, result.compute()));
    }

    @Test
    void minimizeSphereFunctionTest() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int dimention = 20 + rnd.nextInt(30); // Higher number between 20 and 50

        // Define a simple Function for testing
        SphereFunction function = new SphereFunction(dimention);

        // Run Simulated Annealing
        SphereFunction result = (SphereFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            handler);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x(i)) < 10e-5, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x(i)));
        }

        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum. Expected: %.5f, result: %.5f", expected, result.compute()));
    }

    @Test
    void minimizeRastriginFunctionDefaultTest() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int dimention = 20 + rnd.nextInt(30); // Higher number between 20 and 50

        // Define a simple Function for testing
        RastriginFunction function = new RastriginFunction(dimention);

        // Run Simulated Annealing
        RastriginFunction result = (RastriginFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x(i)) < 10e-2, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x(i)));
        }

        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum. Expected: %.5f, result: %.5f", expected, result.compute()));
    }

    @Test
    void minimizeRastriginFunctionTest() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int dimention = 20 + rnd.nextInt(30); // Higher number between 20 and 50

        // Define a simple Function for testing
        RastriginFunction function = new RastriginFunction(dimention);

        // Run Simulated Annealing
        RastriginFunction result = (RastriginFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            handler);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x(i)) < 10e-2, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x(i)));
        }

        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum. Expected: %.5f, result: %.5f", expected, result.compute()));
    }
}
