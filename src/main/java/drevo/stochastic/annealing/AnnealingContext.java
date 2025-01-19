package drevo.stochastic.annealing;

import drevo.stochastic.ProblemType;

/**
 * AnnealingContext is a registry with parameters for the functionality of the Simulated Annealing algorithm, they are:
 * <pre>
 * - initialTemperature
 * It is the initial temperature considering the initial configuration of the solution.
 * - finalTemperature
 * It is the final temperature considering the final (with lock the best) configuration of the solution.
 * - coolingRate
 * It is the rate of the system will cooling in the simulation, that mean the simulation of the cooling.
 * - steps
 * For each temperature the algorithm will try find a beter configuration for the number of steps.
 * - deadline
 * It is a control parameter that put a time limit to run the simulation, in seconds.
 * - problemType
 * Define if to find the problem solution a maximization or minimization is required.
 * </pre>
 * 
 * Together with an implementation of AnnealingFunction, they provide all information needed to run the simulation.
 */
public class AnnealingContext {
    public final double initialTemperature;
    public final double finalTemperature;
    public final double coolingRate;
    public final int steps;
    public final long deadline;
    public final ProblemType problemType;

    public AnnealingContext(ProblemType problemType) {
        this(10000, 0.1, 0.01, 150000, 300, problemType);
    }

    public AnnealingContext(double initialTemperature, double finalTemperature, double coolingRate, int steps, long deadline, ProblemType problemType) {
        if(initialTemperature < 0.0 || finalTemperature < 0.0) {
            throw new IllegalArgumentException("Temperatures must have positive values.");
        }

        if(initialTemperature < finalTemperature) {
            throw new IllegalArgumentException("The initial temperature must be greater then final one.");
        }

        if(coolingRate <= 0.0 || coolingRate > 0.5) {
            throw new IllegalArgumentException("Cooling rate must have positive values less or equals than 0.5.");
        }

        if(steps <= 0) {
            throw new IllegalArgumentException("Steps must have non null positive values.");
        }

        if(deadline <= 0) {
            throw new IllegalArgumentException("Deadline must have non null positive values.");
        }

        this.initialTemperature = initialTemperature;
        this.finalTemperature = finalTemperature;
        this.coolingRate = coolingRate;
        this.steps = steps;
        this.deadline = deadline;
        this.problemType = problemType;
    }

    public double initialTemperature() { return initialTemperature; }

    public double finalTemperature() { return finalTemperature; }

    public double coolingRate() { return coolingRate; }

    public int steps() { return steps; }

    public long deadline() { return deadline; }

    public ProblemType problemType() { return problemType; }

    @Override
    public String toString() {
        return "{AnnealingContext: {" + 
                    "'initialTemperature': " + initialTemperature + 
                    ", 'finalTemperature': " + finalTemperature +
                    ", 'coolingRate': '" + coolingRate + 
                    "', 'steps': '" + steps + 
                    "', 'deadline': '" + deadline + 
                    "', 'problemType': '" + problemType + "'" +
                "}";
    }

    
}
