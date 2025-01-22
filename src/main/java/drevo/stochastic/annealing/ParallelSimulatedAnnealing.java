package drevo.stochastic.annealing;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.ProblemType;

class SinFunction implements AnnealingFunction {
    public final Random rnd = ThreadLocalRandom.current();

    public final double minXDomine = -100;
    public final double maxXDomine = +100;
    public final double minimum    = -1;
    public final double maximum    = +1;

    private double x;

    public SinFunction() {
        reconfigure();
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double t) {
        return Math.sin(5*t - 2);
    }

    @Override
    public void reconfigure() {
        x = rnd.nextDouble() * (maxXDomine - minXDomine) + minXDomine;
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof SinFunction sinFunction) {
            this.x = sinFunction.x;
        }
    }

    @Override
    public boolean isValid() {
        return minXDomine <= x && x <= maxXDomine;
    }

    @Override
    public AnnealingFunction copy() {
        SinFunction clone = new SinFunction();
        clone.x = this.x;
        return clone;
    }
}

public class ParallelSimulatedAnnealing {
    private ParallelSimulatedAnnealing() {
    }
    
    // Configure default Annealing Context for minimize
    private static AnnealingContext minimizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MINIMIZE);

    // Configure default Annealing Context for maximize
    private static AnnealingContext maximizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MAXIMIZE);

    public static AnnealingFunction[] optimize(AnnealingContext ctx, AnnealingFunction function) {
        int cores = 4;// Runtime.getRuntime().availableProcessors();

        int[] executions = new int[cores];
        AnnealingFunction[] result = new AnnealingFunction[cores];

        for(int i = 0; i < cores; executions[i] = i++);

        Arrays.stream(executions).boxed().toList().parallelStream().forEach(i -> result[i] = SimulatedAnnealing.optimize(ctx, function));

        return result;
    }

    public static void main(String[] args) {
        AnnealingFunction[] results = optimize(minimizeDefaultAnnealingContext, new SinFunction());
    }
}
