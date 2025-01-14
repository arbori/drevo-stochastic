package drevo.stochastic.annealing;

import drevo.stochastic.ProblemType;

public abstract class BaseFunctionTest {
    // Configure Annealing Context for minimize
    AnnealingContext minimizeAnnealingContext = new AnnealingContext(
        10000, 0.1, 0.01, 150000, 300, ProblemType.MINIMIZE);

    // Configure Annealing Context for maximize
    AnnealingContext maximizeAnnealingContext = new AnnealingContext(
        10000, 0.1, 0.01, 150000, 300, ProblemType.MAXIMIZE);

    // Configure default Annealing Context for minimize
    AnnealingContext minimizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MINIMIZE);

    // Configure default Annealing Context for maximize
    AnnealingContext maximizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MAXIMIZE);
}
