package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class UnimodalFunctionsTest extends BaseFunctionTest {
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

        // Run Simulated Annealing
        QuadraticFunction result = (QuadraticFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x) < 10e-2, String.format("The x value do not minimize (x - 2)^2. expectedX: %.5f, result.x: %.5f", expectedX, result.x));
    }

    @Test
    void minimizeQuadraticFunctionTest() {
        // Define a simple Function for testing
        QuadraticFunction function = new QuadraticFunction();

        // Run Simulated Annealing
        QuadraticFunction result = (QuadraticFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x) < 10e-2, String.format("The x value do not minimize (x - 2)^2. expectedX: %.5f, result.x: %.5f", expectedX, result.x));
    }
}
