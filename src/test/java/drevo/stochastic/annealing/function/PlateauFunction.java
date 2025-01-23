package drevo.stochastic.annealing.function;

import drevo.stochastic.annealing.AnnealingFunction;

public class PlateauFunction implements AnnealingFunction {
    private double x = Math.random() * 20 - 10;

    public double x() {
        return x;
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double value) {
        if(Math.abs(value) < 1) return 0.0;
        
        return Math.abs(value) - 1;
    }

    @Override
    public void reconfigure() {
        x = Math.random() * 20 - 10; // Adjust x randomly within [-10, 10]
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof PlateauFunction) {
            this.x = ((PlateauFunction) f).x;
        }
    }

    @Override
    public boolean isValid() {
        return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
    }

    @Override
    public AnnealingFunction copy() {
        PlateauFunction clone = new PlateauFunction();
        clone.x = this.x;
        return clone;
    }
}
