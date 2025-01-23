package drevo.stochastic.annealing.function;

import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class QuadraticNoiseFunction implements AnnealingFunction {
    ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private double x = rnd.nextDouble() * 20 - 10;
    
    public final double noise = 0.1;

    public double x() {
        return x;
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double value) {
        double sign = rnd.nextInt(100) % 2 == 0 ? 1 : -1;

        return (value - 2.0) * (value - 2.0) + sign*(rnd.nextDouble() * 2*noise - noise);
    }

    @Override
    public void reconfigure() {
        x = Math.random() * 20 - 10; // Adjust x randomly within [-10, 10]
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof QuadraticNoiseFunction) {
            this.x = ((QuadraticNoiseFunction) f).x;
        }
    }

    @Override
    public boolean isValid() {
        return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
    }

    @Override
    public AnnealingFunction copy() {
        QuadraticNoiseFunction clone = new QuadraticNoiseFunction();
        clone.x = this.x;
        return clone;
    }
}
