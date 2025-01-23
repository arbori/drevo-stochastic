package drevo.stochastic.annealing.function;

import drevo.stochastic.annealing.AnnealingFunction;

public class StepFunction  implements AnnealingFunction {
    private double x = Math.random() * 10 - 5;

    public double x() {
        return x;
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double value) {
        if(value < 1) return 1;
        else if(1 <= value && value <= 2) return 0;
        
        return -1;
    }

    @Override
    public void reconfigure() {
        x = Math.random() * 10 - 5; // Adjust x randomly within [-5, 5]
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof StepFunction) {
            this.x = ((StepFunction) f).x;
        }
    }

    @Override
    public boolean isValid() {
        return -5.0 <= x && x <= 5; // Valid domain: [-10, 10]
    }

    @Override
    public AnnealingFunction copy() {
        StepFunction clone = new StepFunction();
        clone.x = this.x;
        return clone;
    }
}
