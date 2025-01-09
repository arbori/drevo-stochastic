package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class UnimodalFunctionsTest {
    class QuadraticFunction implements AnnealingFunction {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        private double x = rnd.nextDouble() * 20 - 10;
    
        public double getX() {
            return x;
        }

        @Override
        public double compute() {
            return compute(x);
        }
        public double compute(double value) {
            return (value - 2.0) * (value - 2.0);
        }
    
        @Override
        public void reconfigure() {
            x = rnd.nextDouble() * 20 - 10; // Adjust x randomly within [-10, 10]
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof QuadraticFunction) {
                this.x = ((QuadraticFunction) f).x;
            }
        }
    
        @Override
        public boolean isValid() {
            return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
        }
    
        @Override
        public AnnealingFunction copy() {
            QuadraticFunction clone = new QuadraticFunction();
            clone.x = this.x;
            return clone;
        }
    }

    @Test
    void minimizeQuadraticFunctionDefaultTest() {
        // Define a simple Function for testing
        QuadraticFunction function = new QuadraticFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(Math.abs(function.compute(expectedX) - result) < 10e-5, "The x value do not minimize (x - 2)^2");
    }

    @Test
    void minimizeQuadraticFunctionTest() {
        // Define a simple Function for testing
        QuadraticFunction function = new QuadraticFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(Math.abs(function.compute(expectedX) - result) < 10e-5, "The x value do not minimize (x - 2)^2");
    }
}
