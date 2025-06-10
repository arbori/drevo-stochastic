package drevo.stochastic.pso;

import drevo.stochastic.state.StateChange;

public record PSOState(
    int iterations,
    double globalBestFitness,
    double lastGlobalBestFitness,
    String message
) implements StateChange {
}