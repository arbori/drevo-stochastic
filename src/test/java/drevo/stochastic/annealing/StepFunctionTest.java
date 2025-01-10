package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class StepFunctionTest {
    class StepFunction implements AnnealingFunction {
        private double x = Math.random() * 10 - 5;
    
        public double getX() {
            return x;
        }

        @Override
        public double compute() {
            return compute(x);
        }
        public double compute(double value) {
            if(value < 1) return 1;
            else if(1 <= value && value <= 2) return 0;
            
            return -1;
        }
    
        @Override
        public void reconfigure() {
            x = Math.random() * 10 - 5; // Adjust x randomly within [-5, 5]
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof StepFunction) {
                this.x = ((StepFunction) f).x;
            }
        }
    
        @Override
        public boolean isValid() {
            return -5.0 <= x && x <= 5; // Valid domain: [-10, 10]
        }
    
        @Override
        public AnnealingFunction copy() {
            StepFunction clone = new StepFunction();
            clone.x = this.x;
            return clone;
        }
    }

    @Test
    void minimizeStepFunctionDefaultTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 2.0;
        double expected  = -1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(function.getX() > expectedX, "The x value do not minimize step function");
    }

    @Test
    void minimizeStepFunctionTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 2.0;
        double expected  = -1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(function.getX() > expectedX, "The x value do not minimize step function");
    }

    @Test
    void maximizeStepFunctionDefaultTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 1.0;
        double expected  = 1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result));
        assertTrue(function.getX() < expectedX, String.format("The x value do not minimize step function: expected: %.5f, result: %.5f", expected, result));
    }

    @Test
    void maximizeStepFunctionTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 1.0;
        double expected  = 1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result));
        assertTrue(function.getX() < expectedX, String.format("The x value do not minimize step function: expected: %.5f, result: %.5f", expected, result));
    }
}
