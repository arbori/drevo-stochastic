package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class SampleFunctionTest extends BaseFunctionTest {
    private static final int BOUND = 1000;

    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    class SampleFunction implements AnnealingFunction {
        private int size;
        private List<Double> x;
        private int index;
        private double bound;

        public SampleFunction(int size, double bound) {
            if(size <= 0) {
                throw new IllegalArgumentException("Dimention of values must be positive");
            }

            this.size = size;

            if(bound <= 0.0) {
                bound = 20.0;
            }

            this.x = new ArrayList<>(this.size);

            for(int i = 0; i < this.size; i++) {
                this.x.add(rnd.nextDouble(bound));
            }

            this.index = rnd.nextInt(x.size());
        }

        public double getX() {
            return x.get(index);
        }

        @Override
        public double compute() {
            return x.get(index);
        }
    
        @Override
        public void reconfigure() {
            this.index = rnd.nextInt(x.size());
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof SampleFunction sampleFunction && sampleFunction.size == this.size) {
                for(int i = 0; i < size; i++) {
                    x.set(i, sampleFunction.x.get(i));
                }

                this.index = sampleFunction.index;
            }
        }
    
        @Override
        public boolean isValid() {
            return 0 < this.index && this.index < this.x.size();
        }
    
        @Override
        public AnnealingFunction copy() {
            SampleFunction clone = new SampleFunction(this.size, this.bound);
            
            clone.assign(this);

            return clone;
        }
    }

    @Test
    void maximumOptimumDefaultTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(maximizeDefaultAnnealingContext, function);

        double expectedResult = Double.NEGATIVE_INFINITY;

        int indexMaximum = -1;

        for(int i = 0; i < function.x.size(); i++) {
            if(expectedResult < function.x.get(i)) {
                indexMaximum = i;
                expectedResult = function.x.get(i);
            }
        }

        assertEquals(indexMaximum, result.index, String.format("indexMaximum: %d => value: %.5f, function.index: %d => value: %.5f", indexMaximum, result.x.get(indexMaximum), result.index, result.compute()));
        assertEquals(expectedResult, result.x.get(result.index), String.format("expectedResult: %f, result: %f", expectedResult, result.x.get(result.index)));
    }

    @Test
    void maximumOptimumTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(maximizeAnnealingContext, function);

        double expectedResult = Double.NEGATIVE_INFINITY;

        int indexMaximum = -1;

        for(int i = 0; i < function.x.size(); i++) {
            if(expectedResult < function.x.get(i)) {
                indexMaximum = i;
                expectedResult = function.x.get(i);
            }
        }

        assertEquals(indexMaximum, result.index, String.format("indexMaximum: %d => value: %.5f, function.index: %d => value: %.5f", indexMaximum, result.x.get(indexMaximum), result.index, result.compute()));
        assertEquals(expectedResult, result.compute(), String.format("expectedResult: %f, result: %f", expectedResult, result.compute()));
    }

    @Test
    void minimumOptimumDefaultTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(minimizeDefaultAnnealingContext, function);

        double expectedResult = Double.POSITIVE_INFINITY;

        int indexMinimum = -1;

        for(int i = 0; i < function.x.size(); i++) {
            if(expectedResult > function.x.get(i)) {
                indexMinimum = i;
                expectedResult = function.x.get(i);
            }
        }

        assertEquals(indexMinimum, result.index, String.format("indexMinimum: %d => value: %.5f, function.index: %d => value: %.5f", indexMinimum, result.x.get(indexMinimum), result.index, result.compute()));
        assertEquals(expectedResult, result.compute(), String.format("expectedResult: %f, result: %f", expectedResult, result.compute()));
    }

    @Test
    void minimumOptimumTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(minimizeAnnealingContext, function);

        double expectedResult = Double.POSITIVE_INFINITY;

        int indexMinimum = -1;

        for(int i = 0; i < function.x.size(); i++) {
            if(expectedResult > function.x.get(i)) {
                indexMinimum = i;
                expectedResult = function.x.get(i);
            }
        }

        assertEquals(indexMinimum, result.index, String.format("indexMinimum: %d => value: %.5f, function.index: %d => value: %.5f", indexMinimum, result.x.get(indexMinimum), result.index, result.compute()));
        assertEquals(expectedResult, result.compute(), String.format("expectedResult: %f, result: %f", expectedResult, result.compute()));
    }
}

