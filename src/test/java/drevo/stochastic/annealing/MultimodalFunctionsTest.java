package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

/**
 * Unit test for simple App.
 */
class MultimodalFunctionsTest {

    class SinXFunction implements AnnealingFunction {
        private double x = 0;
    
        public double getX() {
            return x;
        }

        @Override
        public double compute() {
            return Math.sin(x);
        }
        public double compute(double value) {
            return Math.sin(value);
        }
    
        @Override
        public void reconfigure() {
            x = Math.random() * 2* Math.PI; // Adjust x randomly within [0, 2pi]
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof SinXFunction) {
                this.x = ((SinXFunction) f).x;
            }
        }
    
        @Override
        public boolean isValid() {
            return 0.0 <= x && x <= 2*Math.PI; // Valid domain: [0, 2pi]
        }
    
        @Override
        public AnnealingFunction copy() {
            SinXFunction clone = new SinXFunction();
            clone.x = this.x;
            return clone;
        }
    }

    class Cos5xFunction implements AnnealingFunction {
        private double x = Math.random() * 2* Math.PI;
    
        public double getX() {
            return x;
        }
        
        @Override
        public double compute() {
            return Math.cos(5*x);
        }
        public double compute(double value) {
            return Math.cos(5*value);
        }
    
        @Override
        public void reconfigure() {
            x = Math.random() * 2* Math.PI; // Adjust x randomly within [0, 2pi]
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof Cos5xFunction) {
                this.x = ((Cos5xFunction) f).x;
            }
        }
    
        @Override
        public boolean isValid() {
            return 0.0 <= x && x <= 2*Math.PI; // Valid domain: [0, 2pi]
        }
    
        @Override
        public AnnealingFunction copy() {
            Cos5xFunction clone = new Cos5xFunction();
            clone.x = this.x;
            return clone;
        }
    }
    
    @Test
    void minimizeSinXDefaultTest() {
        // Define a simple Function for testing
        SinXFunction function = new SinXFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 3.0*Math.PI/2.0;
        double expected = -1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(Math.abs(function.compute(expectedX) - result) < 10e-5, "The x value do not minimize cos(5x)");
    }

    @Test
    void minimizeSinXTest() {
        // Define a simple Function for testing
        SinXFunction function = new SinXFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = 3.0*Math.PI/2.0;
        double expected = -1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(Math.abs(function.compute(expectedX) - result) < 10e-5, "The x value do not minimize cos(5x)");
    }

    @Test
    void maximizeSinXDefaultTest() {
        // Define a simple Function for testing
        SinXFunction function = new SinXFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = Math.PI/2.0;
        double expected = 1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(Math.abs(function.compute(expectedX) - result) < 10e-5, "The x value do not minimize cos(5x)");
    }

    @Test
    void maximizeSinXTest() {
        // Define a simple Function for testing
        SinXFunction function = new SinXFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedX = Math.PI/2.0;
        double expected = 1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");
        assertTrue(Math.abs(function.compute(expectedX) - result) < 10e-5, "The x value do not minimize cos(5x)");
    }

    @Test
    void minimizeCos5xDefaultTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        List<Double> expectedX = Arrays.asList( Math.PI/5.0, 3.0*Math.PI/5.0, Math.PI, 7.0*Math.PI/5.0, 9.0*Math.PI/5.0 );
        double  expected = -1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");

        expectedX.forEach(x -> 
            assertTrue(Math.abs(function.compute(x) - result) < 10e-5, "The x value do not minimize cos(5x)")
        );
    }

    @Test
    void minimizeCos5xTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(100000, 0.1, 0.01, 1000, 15000, ProblemType.MINIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        List<Double> expectedX = Arrays.asList( Math.PI/5.0, 3.0*Math.PI/5.0, Math.PI, 7.0*Math.PI/5.0, 9.0*Math.PI/5.0 );
        double  expected = -1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, String.format("Result should be close to the minimum. Expected: %02.5f, result: %02.5f", expected, result));

        expectedX.forEach(x -> 
            assertTrue(Math.abs(function.compute(x) - result) < 10e-5, "The x value do not minimize cos(5x)")
        );
    }
    
    @Test
    void maximizeCos5xDefaultTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        List<Double> expectedX = Arrays.asList( 0.0, 2.0*Math.PI/5.0, 4.0*Math.PI/5.0, 6.0*Math.PI/5.0, 8.0*Math.PI/5.0 );
        double expected = 1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");

        expectedX.forEach(x -> 
            assertTrue(Math.abs(function.compute(x) - result) < 10e-5, "The x value do not minimize cos(5x)")
        );
    }

    @Test
    void maximizeCos5xTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        List<Double> expectedX = Arrays.asList( 0.0, 2.0*Math.PI/5.0, 4.0*Math.PI/5.0, 6.0*Math.PI/5.0, 8.0*Math.PI/5.0 );
        double expected = 1.0;

        // Assert that we found a reasonable solution
        double result = function.compute();
        assertTrue(Math.abs(expected - result) < 10e-5, "Result should be close to the minimum");

        expectedX.forEach(x -> 
            assertTrue(Math.abs(function.compute(x) - result) < 10e-5, "The x value do not minimize cos(5x)")
        );
    }
}
