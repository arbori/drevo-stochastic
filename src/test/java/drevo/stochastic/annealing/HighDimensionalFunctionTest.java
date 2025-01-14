package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class HighDimensionalFunctionTest extends BaseFunctionTest {
    class SphereFunction implements AnnealingFunction {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        private int dimention;
        private List<Double> x;
        private double minX = -10;
        private double maxX = +10;

        public SphereFunction(int dimention) {
            this.dimention = dimention;
            x = new ArrayList<>(dimention);
            
            for(int i = 0; i < dimention; i++) {
                x.add(rnd.nextDouble(maxX - minX));
            }
        }

        public List<Double> getX() {
            return x;
        }

        @Override
        public double compute() {
            return compute(x);
        }
        public double compute(List<Double> value) {
            return value.stream().mapToDouble(n -> n*n).sum();
        }
    
        @Override
        public void reconfigure() {
            int size = rnd.nextInt(x.size());
            int idx;

            for(int i = 0; i < size; i++) {
                idx = rnd.nextInt(x.size());
                x.set(idx, x.get(idx)*(1 + (rnd.nextInt()%2 == 0 ? 1 : -1)*rnd.nextDouble()));
            }
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof SphereFunction sphereFunction && sphereFunction.dimention == this.dimention) {
                for(int i = 0; i < dimention; i++) {
                    x.set(i, sphereFunction.x.get(i));
                }
            }
        }
    
        @Override
        public boolean isValid() {
            return !x.stream().filter(value -> -10.0 > value && value > 10).findFirst().isPresent();
        }
    
        @Override
        public AnnealingFunction copy() {
            SphereFunction clone = new SphereFunction(dimention);
            
            clone.assign(this);
            
            return clone;
        }
    }

    class RastriginFunction implements AnnealingFunction {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        private int dimention;

        private List<Double> x;
        private double minX = -10;
        private double maxX = +10;

        public RastriginFunction(int dimention) {
            this.dimention = dimention;

            x = new ArrayList<>(this.dimention);
            
            for(int i = 0; i < this.dimention; i++) {
                x.add(rnd.nextDouble(maxX - minX));
            }
        }

        public List<Double> getX() {
            return x;
        }

        @Override
        public double compute() {
            return compute(x);
        }
        public double compute(List<Double> value) {
            return 10*dimention + value.stream().mapToDouble(v -> v*v - 10*Math.cos(2*Math.PI*v)).sum();
        }
    
        @Override
        public void reconfigure() {
            int size = rnd.nextInt(x.size());
            int idx;

            for(int i = 0; i < size; i++) {
                idx = rnd.nextInt(x.size());
                x.set(idx, x.get(idx)*(1 + (rnd.nextInt()%2 == 0 ? 1 : -1)*rnd.nextDouble()));
            }
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof RastriginFunction rastriginFunction && rastriginFunction.dimention == this.dimention) {
                for(int i = 0; i < dimention; i++) {
                    x.set(i, rastriginFunction.x.get(i));
                }
            }
        }
    
        @Override
        public boolean isValid() {
            return !x.stream().filter(value -> -5.12 > value && value > 5.12).findFirst().isPresent();
        }
    
        @Override
        public AnnealingFunction copy() {
            RastriginFunction clone = new RastriginFunction(dimention);
            
            clone.assign(this);
            
            return clone;
        }
    }

    @Test
    void minimizeSphereFunctionDefaultTest() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int dimention = 20 + rnd.nextInt(30); // Higher number between 20 and 50

        // Define a simple Function for testing
        SphereFunction function = new SphereFunction(dimention);

        // Run Simulated Annealing
        SphereFunction result = (SphereFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x.get(i)) < 10e-2, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x.get(i)));
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
        SphereFunction result = (SphereFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x.get(i)) < 10e-5, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x.get(i)));
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
        RastriginFunction result = (RastriginFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x.get(i)) < 10e-2, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x.get(i)));
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
        RastriginFunction result = (RastriginFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        double expectedX = 0.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        for(int i = 0; i < dimention; i++) {
            assertTrue(Math.abs(expectedX - result.x.get(i)) < 10e-2, String.format("The x_%d set do not minimize ∑ x^2. Value: %.5f", i, result.x.get(i)));
        }

        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum. Expected: %.5f, result: %.5f", expected, result.compute()));
    }
}
