package drevo.stochastic.pso;

import java.util.function.Function;

public record PSOContext<T extends Particle<T>> (
    Function<T, Double> fitnessFunction,
    int maxIterations,
    double inertiaWeight,
    double cognitiveWeight,
    double socialWeight
) {
    /**
     * Creates a new PSOContext with the specified parameters.
     *
     * @param fitnessFunction the function to evaluate particle fitness
     * @param maxIterations the maximum number of iterations for the PSO algorithm
     * @param inertiaWeight the weight applied to the particle's previous velocity
     * @param cognitiveWeight the weight applied to the particle's personal best position
     * @param socialWeight the weight applied to the global best position of the swarm
     */
    public PSOContext {
        if (fitnessFunction == null) {
            throw new NullPointerException("fitnessFunction cannot be null");
        }
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations must be greater than 0");
        }
        if (inertiaWeight < 0 || cognitiveWeight < 0 || socialWeight < 0) {
            throw new IllegalArgumentException("Weights must be non-negative");
        }
    }
}
