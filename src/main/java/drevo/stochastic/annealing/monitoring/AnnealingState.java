package drevo.stochastic.annealing.monitoring;

public record AnnealingState(
    double temperature, 
    double initialEnergy, 
    double finalEnergy,
    double delta,
    double probability, 
    int currentStep,
    boolean accepted,
    String message) {
}
