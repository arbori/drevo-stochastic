package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class MixedIntegerFunctionTest {
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    class KnapsackFunction implements AnnealingFunction {
        private int dimention;
        private List<Integer> x;
        private List<Double> v;
        private List<Double> w;
        private double penalty;
        private double restrition;

        public KnapsackFunction(List<Double> v, List<Double> w, double penalty, double restrition) {
            if(v.size() <= 0 || w.size() <= 0) {
                throw new IllegalArgumentException("Dimention of values and weight must be positive");
            }
            if(v.size() != w.size()) {
                throw new IllegalArgumentException("Dimention of values and weight must be equals");
            }

            this.dimention = v.size();
            this.penalty = penalty;
            this.restrition = restrition;

            this.x = new ArrayList<>(this.dimention);
            this.v = new ArrayList<>(this.dimention);
            this.w = new ArrayList<>(this.dimention);

            for(int i = 0; i < this.dimention; i++) {
                this.x.add(rnd.nextInt(1));
            }

            this.v.addAll(v);
            this.w.addAll(w);
        }

        public List<Integer> getX() {
            return x;
        }

        public List<Double> getV() {
            return v;
        }

        public List<Double> getW() {
            return w;
        }

        @Override
        public double compute() {
            return compute(x);
        }

        public double compute(List<Integer> value) {
            double sumXV = 0.0;
            double sumPenalty = 0.0;
            
            for(int i = 0; i < value.size(); i++) {
                sumXV += v.get(i) * value.get(i);
                sumPenalty += w.get(i) * value.get(i);
            }

            sumPenalty = (sumPenalty >= restrition) ? penalty * (sumPenalty - restrition) : 0.0;

            return sumXV - sumPenalty;
        }
    
        @Override
        public void reconfigure() {
            int size = rnd.nextInt(x.size());
            int idx;

            for(int i = 0; i < size; i++) {
                idx = rnd.nextInt(x.size());
                x.set(idx, 1 - x.get(idx));
            }
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof KnapsackFunction knapsackFunction && knapsackFunction.dimention == this.dimention) {
                for(int i = 0; i < dimention; i++) {
                    x.set(i, knapsackFunction.x.get(i));
                    v.set(i, knapsackFunction.v.get(i));
                    w.set(i, knapsackFunction.w.get(i));
                }
            }
        }
    
        @Override
        public boolean isValid() {
            double sumPenalty = 0.0;
            
            for(int i = 0; i < dimention; i++) {
                sumPenalty += w.get(i) * x.get(i);
            }

            return sumPenalty < this.restrition;
        }
    
        @Override
        public AnnealingFunction copy() {
            KnapsackFunction clone = new KnapsackFunction(this.v, this.w, this.penalty, this.restrition);
            
            clone.x.clear();

            for(int i = 0; i < this.dimention; i++) {
                clone.x.add(this.x.get(i));
            }

            return clone;
        }
    }

    private double maximumValue(List<Double> v, List<Double> w, double restriction) {
        if(v.size() != w.size()) {
            throw new IllegalArgumentException("Dimention of values, weight and x's values must be equals");
        }

        int dimention = v.size();

        List<Integer> x = new ArrayList<>();
        for(int i = 0; i < dimention; i++) {
            x.add(0);
        }

        double sumXV;
        double maxXV = Double.NEGATIVE_INFINITY;
        double sumPenalty;

        while(x.stream().mapToInt(n -> n).sum() < x.size()) {        
            sumXV = 0.0;
            sumPenalty = 0.0;

            for(int i = 0; i < x.size(); i++) {
                sumXV += v.get(i) * x.get(i);
                sumPenalty += w.get(i) * x.get(i);
            }

            if(sumPenalty < restriction && maxXV < sumXV) {
                maxXV = sumXV;
            }

            incrementBinaryList(x);
        }

        return maxXV;
    }

    // Function to increment the binary list
    private void incrementBinaryList(List<Integer> binaryList) {
        int n = binaryList.size();

        for (int i = 0; i < n; i++) {
            if (binaryList.get(i) == 0) {
                binaryList.set(i, 1);
                return; // Stop once there's no carry
            } else {
                binaryList.set(i, 0); // Carry over
            }
        }
    }

    @Test
    void maximumBasicTest() {
        List<Double> v = Arrays.asList(20.0, 15.0, 10.0);
        List<Double> w = Arrays.asList(5.0, 8.0, 3.0);
        double penalty = 100.0;
        double restrition = 10.0;

        KnapsackFunction knapsackFunction = new KnapsackFunction(v, w, penalty, restrition);

        double expectedResult = 30.0;
        double result = knapsackFunction.compute(Arrays.asList(1, 0, 1));

        assertEquals(expectedResult, result);

        expectedResult = -265.0;
        result = knapsackFunction.compute(Arrays.asList(1, 1, 0));

        assertEquals(expectedResult, result);
    }

    @Test
    void maximumOptimumTest() {
        List<Double> v = new ArrayList<>();
        List<Double> w = new ArrayList<>();
        double penalty = 100.0;
        double restrition = 10.0;

        int dimention = rnd.nextInt(24);

        for(int i = 0; i < dimention; i++) {
            v.add(rnd.nextDouble(30));
            w.add(rnd.nextDouble(10));
        }

        KnapsackFunction function = new KnapsackFunction(v, w, penalty, restrition);

        // Configure Annealing Context
        AnnealingContext ctx = new AnnealingContext(ProblemType.MAXIMIZE);

        // Run Simulated Annealing
        SimulatedAnnealing.optimize(ctx, function);

        double expectedResult = maximumValue(v, w, restrition);
        double result = function.compute();

        assertEquals(expectedResult, result);
    }
}
