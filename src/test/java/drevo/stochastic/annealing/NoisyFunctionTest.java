package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class NoisyFunctionTest extends BaseFunctionTest {
    class QuadraticNoiseFunction implements AnnealingFunction {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        private double x = rnd.nextDouble() * 20 - 10;
        
        public final double noise = 0.1;

        public double getX() {
            return x;
        }

        @Override
        public double compute() {
            return compute(x);
        }
        public double compute(double value) {
            double sign = rnd.nextInt(100) % 2 == 0 ? 1 : -1;

            return (value - 2.0) * (value - 2.0) + sign*(rnd.nextDouble() * 2*noise - noise);
        }
    
        @Override
        public void reconfigure() {
            x = Math.random() * 20 - 10; // Adjust x randomly within [-10, 10]
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof QuadraticNoiseFunction) {
                this.x = ((QuadraticNoiseFunction) f).x;
            }
        }
    
        @Override
        public boolean isValid() {
            return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
        }
    
        @Override
        public AnnealingFunction copy() {
            QuadraticNoiseFunction clone = new QuadraticNoiseFunction();
            clone.x = this.x;
            return clone;
        }
    }

    @Test
    void minimizeQuadraticNoiseFunctionDefaultTest() {
        // Define a simple Function for testing
        QuadraticNoiseFunction function = new QuadraticNoiseFunction();

        // Run Simulated Annealing
        QuadraticNoiseFunction result = (QuadraticNoiseFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(expectedMin < resultY && resultY < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, resultY));
        assertTrue(expectedMinX < result.x && result.x < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, result.x));
    }

    @Test
    void minimizeQuadraticNoiseFunctionTest() {
        // Define a simple Function for testing
        QuadraticNoiseFunction function = new QuadraticNoiseFunction();

        // Run Simulated Annealing
        QuadraticNoiseFunction result = (QuadraticNoiseFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(expectedMin < resultY && resultY < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, result.x));
        assertTrue(expectedMinX < result.x && result.x < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, result.x));
    }
}
