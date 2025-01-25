package drevo.stochastic.annealing.monitoring;

@FunctionalInterface
public interface StateChangeHandler {
    void handleStateChange(AnnealingState state);
}
