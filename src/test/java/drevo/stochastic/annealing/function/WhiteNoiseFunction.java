package drevo.stochastic.annealing.function;

import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class WhiteNoiseFunction implements AnnealingFunction {
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private double x;
    private double noiseLevel;

    public WhiteNoiseFunction(double noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public double noiseLevel() {
        return noiseLevel;
    }

    @Override
    public double compute() {
        return x;
    }

    @Override
    public void reconfigure() {
        x = (rnd.nextInt() % 2 == 0 ? noiseLevel : -noiseLevel) * rnd.nextDouble();
    }

    @Override
    public void assign(AnnealingFunction f) {
        if(f instanceof WhiteNoiseFunction whiteNoise) {
            x = whiteNoise.x;
            noiseLevel = whiteNoise.noiseLevel;
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public AnnealingFunction copy() {
        WhiteNoiseFunction clone = new WhiteNoiseFunction(this.noiseLevel);

        clone.x = this.x;

        return clone;
    }
}
