package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class PlateauFunctionTest {
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

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 1.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, result));
        assertTrue(Math.abs(function.getX()) < expectedX, String.format("The x value do not minimize step function: expected: %f, result: %f", expected, result));
    }

    @Test
    void minimizePlateauFunctionTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 1.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, result));
        assertTrue(Math.abs(function.getX()) < expectedX, String.format("The x value do not minimize step function: expected: %f, result: %f", expected, result));
    }

    @Test
    void maximizePlateauFunctionDefaultTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 1.0;
        double expected  = 9.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, result));
        assertTrue(Math.abs(function.getX()) >= expectedX, String.format("The x value do not minimize step function: expected: %f, result: %f", expected, result));
    }

    @Test
    void maximizePlateauFunctionTest() {
        // Define a simple Function for testing
        PlateauFunction function = new PlateauFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 1.0;
        double expected  = 9.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, String.format("Result should be close to the minimum: expected: %f, result: %f", expected, result));
        assertTrue(Math.abs(function.getX()) >= expectedX, String.format("The x value do not minimize step function: expected: %f, result: %f", expected, result));
    }
}
