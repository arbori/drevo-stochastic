package drevo.stochastic.annealing;

import drevo.stochastic.ProblemType;
import drevo.stochastic.annealing.monitoring.StateChangeHandler;

public abstract class BaseFunctionTest {
    // Configure Annealing Context for minimize
    AnnealingContext minimizeAnnealingContext = new AnnealingContext(
        10000, 0.1, 0.01, 150000, 300, -1, 300, ProblemType.MINIMIZE);

    // Configure Annealing Context for maximize
    AnnealingContext maximizeAnnealingContext = new AnnealingContext(
        10000, 0.1, 0.01, 150000, 300, -1, 300, ProblemType.MAXIMIZE);

    // Configure default Annealing Context for minimize
    AnnealingContext minimizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MINIMIZE);

    // Configure default Annealing Context for maximize
    AnnealingContext maximizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MAXIMIZE);

    StateChangeHandler handler = state -> {
        if (state.currentStep() == 0 && state.temperature() == 0.0) {
            System.out.println(state.message());
        } else {
            System.out.print(state.temperature());
            System.out.print("\t");
            System.out.print(state.currentStep());
            System.out.print("\t");
            System.out.print(state.context().problemType().valueOf() * state.initialEnergy());
            System.out.print("\t");
            System.out.println(state.context().problemType().valueOf() * state.finalEnergy());
        }
    };
}
