package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class SampleFunctionTest {
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    class SampleFunction implements AnnealingFunction {
        private int dimention;
        private List<Double> x;
        private int index;

        public SampleFunction(int dimention, double bound) {
            if(dimention <= 0) {
                throw new IllegalArgumentException("Dimention of values must be positive");
            }

            this.dimention = dimention;

            this.x = new ArrayList<>(this.dimention);

            for(int i = 0; i < this.dimention; i++) {
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
            if (f instanceof SampleFunction sampleFunction && sampleFunction.dimention == this.dimention) {
                for(int i = 0; i < dimention; i++) {
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
            SampleFunction clone = new SampleFunction(this.dimention, 20.0);
            
            clone.assign(this);

            return clone;
        }
    }

    @Test
    void maximumOptimumDefaultTest() {
        int dimention = rnd.nextInt(10000);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedResult = Double.NEGATIVE_INFINITY;
        double result = function.compute();

        int indexMaximum = -1;

        for(int i = 0; i < function.x.size(); i++) {
            if(expectedResult < function.x.get(i)) {
                indexMaximum = i;
                expectedResult = function.x.get(i);
            }
        }

        assertEquals(indexMaximum, function.index, String.format("indexMaximum: %d, function.index: %d", indexMaximum, function.index));
        assertEquals(expectedResult, result, String.format("expectedResult: %f, result: %f", expectedResult, result));
    }

    @Test
    void maximumOptimumTest() {
        int dimention = rnd.nextInt(10000);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(10000, 0.1, 0.01, 1000, 1500, ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedResult = Double.NEGATIVE_INFINITY;
        double result = function.compute();

        int indexMaximum = -1;

        for(int i = 0; i < function.x.size(); i++) {
            if(expectedResult < function.x.get(i)) {
                indexMaximum = i;
                expectedResult = function.x.get(i);
            }
        }

        assertEquals(indexMaximum, function.index, String.format("indexMaximum: %d, function.index: %d", indexMaximum, function.index));
        assertEquals(expectedResult, result, String.format("expectedResult: %f, result: %f", expectedResult, result));
    }
}

