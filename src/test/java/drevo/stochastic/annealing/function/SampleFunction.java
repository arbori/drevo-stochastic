package drevo.stochastic.annealing.function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class SampleFunction implements AnnealingFunction {
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

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

    public double size() {
        return size;
    }

    public double x() {
        return x.get(index);
    }

    public double x(int i) {
        return x.get(i);
    }

    public int index() {
        return index;
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
