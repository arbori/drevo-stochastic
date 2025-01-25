package drevo.stochastic.annealing.function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class SphereFunction implements AnnealingFunction {
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

        public List<Double> x() {
            return x;
        }

        public double x(int i) {
            return x.get(i);
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
