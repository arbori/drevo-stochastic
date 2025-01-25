package drevo.stochastic.annealing.monitoring;

import drevo.stochastic.annealing.AnnealingContext;

public record AnnealingState(
    double temperature, 
    double initialEnergy, 
    double finalEnergy,
    double delta,
    double probability,
    double bestValue,
    AnnealingContext context,
    int currentStep,
    boolean accepted,
    String message) {
}
