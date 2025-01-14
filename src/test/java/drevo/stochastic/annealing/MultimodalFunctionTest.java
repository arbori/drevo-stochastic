package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
class MultimodalFunctionTest extends BaseFunctionTest {

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

        // Run Simulated Annealing
        SinXFunction result = (SinXFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        double expectedX = 3.0*Math.PI/2.0;
        double expected = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x) < 10e-2, String.format("The x value do not minimize sin(x). expectedX: %.5f, result.x: %.5f", expectedX, result.x));
    }

    @Test
    void minimizeSinXTest() {
        // Define a simple Function for testing
        SinXFunction function = new SinXFunction();

        // Run Simulated Annealing
        SinXFunction result = (SinXFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        double expectedX = 3.0*Math.PI/2.0;
        double expected = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x) < 10e-2, String.format("The x value do not minimize sin(x). expectedX: %.5f, result.x: %.5f", expectedX, result.x));
    }

    @Test
    void maximizeSinXDefaultTest() {
        // Define a simple Function for testing
        SinXFunction function = new SinXFunction();

        // Run Simulated Annealing
        SinXFunction result = (SinXFunction ) SimulatedAnnealing.optimize(maximizeDefaultAnnealingContext, function);

        double expectedX = Math.PI/2.0;
        double expected = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't maximize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x) < 10e-2, String.format("The x value do not maximize sin(x). expectedX: %.5f, result.x: %.5f", expectedX, result.x));
    }

    @Test
    void maximizeSinXTest() {
        // Define a simple Function for testing
        SinXFunction function = new SinXFunction();

        // Run Simulated Annealing
        SinXFunction result = (SinXFunction) SimulatedAnnealing.optimize(maximizeAnnealingContext, function);

        double expectedX = Math.PI/2.0;
        double expected = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't maximize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x) < 10e-2, String.format("The x value do not maximize sin(x). expectedX: %.5f, result.x: %.5f", expectedX, result.x));
    }

    @Test
    void minimizeCos5xDefaultTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Run Simulated Annealing
        Cos5xFunction result = (Cos5xFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        List<Double> expectedX = Arrays.asList( Math.PI/5.0, 3.0*Math.PI/5.0, Math.PI, 7.0*Math.PI/5.0, 9.0*Math.PI/5.0 );
        double  expected = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));

        boolean exist = false;
        for(Double x: expectedX) {
            exist |= Math.abs(x - result.x) < 10e-2;
        }
        assertTrue(exist, String.format("The x value do not minimize cos(5x). result.x: %.5f", result.x));
    }

    @Test
    void minimizeCos5xTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Run Simulated Annealing
        Cos5xFunction result = (Cos5xFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        List<Double> expectedX = Arrays.asList( Math.PI/5.0, 3.0*Math.PI/5.0, Math.PI, 7.0*Math.PI/5.0, 9.0*Math.PI/5.0 );
        double  expected = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));

        boolean exist = false;
        for(Double x: expectedX) {
            exist |= Math.abs(x - result.x) < 10e-2;
        }
        assertTrue(exist, String.format("The x value do not minimize cos(5x). result.x: %.5f", result.x));
    }
    
    @Test
    void maximizeCos5xDefaultTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Run Simulated Annealing
        Cos5xFunction result = (Cos5xFunction) SimulatedAnnealing.optimize(maximizeDefaultAnnealingContext, function);

        List<Double> expectedX = Arrays.asList( 0.0, 2.0*Math.PI/5.0, 4.0*Math.PI/5.0, 6.0*Math.PI/5.0, 8.0*Math.PI/5.0 );
        double expected = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't maximize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));

        boolean exist = false;
        for(Double x: expectedX) {
            exist |= Math.abs(x - result.x) < 10e-2;
        }
        assertTrue(exist, String.format("The x value do not maximize cos(5x). result.x: %.5f", result.x));
    }

    @Test
    void maximizeCos5xTest() {
        // Define a simple Function for testing
        Cos5xFunction function = new Cos5xFunction();

        // Run Simulated Annealing
        Cos5xFunction result = (Cos5xFunction) SimulatedAnnealing.optimize(maximizeAnnealingContext, function);

        List<Double> expectedX = Arrays.asList( 0.0, 2.0*Math.PI/5.0, 4.0*Math.PI/5.0, 6.0*Math.PI/5.0, 8.0*Math.PI/5.0 );
        double expected = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't maximize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));

        boolean exist = false;
        for(Double x: expectedX) {
            exist |= Math.abs(x - result.x) < 10e-2;
        }
        assertTrue(exist, String.format("The x value do not maximize cos(5x). result.x: %.5f", result.x));
    }
}
