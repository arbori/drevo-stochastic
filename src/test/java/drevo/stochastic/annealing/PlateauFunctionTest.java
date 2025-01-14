package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PlateauFunctionTest extends BaseFunctionTest {
    class PlateauFunction implements AnnealingFunction {
        private double x = Math.random() * 20 - 10;
    
        public double getX() {
            return x;
        }

        @Override
        public double compute() {
            return compute(x);
        }
        public double compute(double value) {
            if(Math.abs(value) < 1) return 0.0;
            
            return Math.abs(value) - 1;
        }
    
        @Override
        public void reconfigure() {
            x = Math.random() * 20 - 10; // Adjust x randomly within [-10, 10]
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof PlateauFunction) {
                this.x = ((PlateauFunction) f).x;
            }
        }
    
        @Override
        public boolean isValid() {
            return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
        }
    
        @Override
        public AnnealingFunction copy() {
            PlateauFunction clone = new PlateauFunction();
            clone.x = this.x;
            return clone;
        }
    }

    @Test
    void minimizePlateauFunctionDefaultTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        double expectedX = 1.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, resultY));
        assertTrue(Math.abs(result.x) < expectedX, String.format("The x value do not minimize step function: expectedX: %f, result.x: %f", expectedX, result.x));
    }

    @Test
    void minimizePlateauFunctionTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        double expectedX = 1.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, resultY));
        assertTrue(Math.abs(result.x) < expectedX, String.format("The x value do not minimize step function: expectedX: %f, result.x: %f", expectedX, result.x));
    }

    @Test
    void maximizePlateauFunctionDefaultTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(maximizeDefaultAnnealingContext, function);

        double expectedX = 1.0;
        double expected  = 9.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the maximum: expected: %f, result: %f", expected, resultY));
        assertTrue(expectedX <= Math.abs(result.x), String.format("The x value do not maximize step function: expectedX: %f, result.x: %f", expectedX, result.x));
    }

    @Test
    void maximizePlateauFunctionTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Run Simulated Annealing
        PlateauFunction result = (PlateauFunction) SimulatedAnnealing.optimize(maximizeAnnealingContext, function);

        double expectedX = 1.0;
        double expected  = 9.0;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(Math.abs(expected - resultY) < 10e-5, String.format("Result should be close to the maximum: expected: %f, result: %f", expected, resultY));
        assertTrue(expectedX <= Math.abs(result.x), String.format("The x value do not maximize step function: expectedX: %f, result.x: %f", expectedX, result.x));
    }
}
