package drevo.stochastic.annealing.function;

import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class QuadraticFunction  implements AnnealingFunction {
    ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private double x = rnd.nextDouble() * 20 - 10;

    public double x() {
        return x;
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double value) {
        return (value - 2.0) * (value - 2.0);
    }

    @Override
    public void reconfigure() {
        x = rnd.nextDouble() * 20 - 10; // Adjust x randomly within [-10, 10]
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof QuadraticFunction) {
            this.x = ((QuadraticFunction) f).x;
        }
    }

    @Override
    public boolean isValid() {
        return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
    }

    @Override
    public AnnealingFunction copy() {
        QuadraticFunction clone = new QuadraticFunction();
        clone.x = this.x;
        return clone;
    }
}
