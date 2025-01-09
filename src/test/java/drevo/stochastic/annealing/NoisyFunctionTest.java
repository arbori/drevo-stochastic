package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class NoisyFunctionTest {
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

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(expectedMin < result && result < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, result));
        assertTrue(expectedMinX < function.getX() && function.getX() < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, function.getX()));
    }

    @Test
    void minimizeQuadraticNoiseFunctionTest() {
        // Define a simple Function for testing
        QuadraticNoiseFunction function = new QuadraticNoiseFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(expectedMin < result && result < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, result));
        assertTrue(expectedMinX < function.getX() && function.getX() < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, function.getX()));
    }
}
