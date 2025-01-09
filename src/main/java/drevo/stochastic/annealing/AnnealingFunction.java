package drevo.stochastic.annealing;

/**
 * AnnealingFunction is the interface that must be implemented to represents a 
 * particular problem, it implements the objective function and must have the 
 * set of information that represents the problem. These informations will be 
 * changed, trying to find a good solution, minimizing the objective function.
 */
public interface AnnealingFunction {
    /**
     * <p>Return the value of the objective function for the problem.
     * 
     * <p>Here, you need to understand what the objective function is, because 
     * Simulated Annealing will always try to find a configuration that minimizes 
     * the objective function, it is the nature of the algorithm, it simulates a 
     * cooling process. Because that, the objective function used in the algorithm 
     * can be a little diferent of the real objective function.
     * 
     * <p>If the model used a function that need be minimize, it can be used directed 
     * in the algorithm. In the other way, it is enought put a negative sign in the 
     * function. It means, if f(.) is he objective function of the problem and need be 
     * minimal, use directly. But, if f(.) must be maximum, use -f(.).
     */
    double compute();

    /**
     * <p> Let's imaging that the solution candidate of the problem is a vector 
     * of 3D space. This method change a little its componests, make it represents 
     * other vector in the space. 
     * <p> That is the idea of reconfigure(), change the model's value(s) to 
     * representing another point in the solution domain. It is part of the 
     * cooling process.
     */
    void reconfigure();

    /**
     * Copy the value and states from another instance, that could representing another point in the solution domain.
     */
    void assign(AnnealingFunction f);

    /**
     * Check if the current configuration is a valid point in the solution domain.
     */
    boolean isValid();

    /**
     * Clone the current instance of the Function.
     */
    AnnealingFunction copy();
}
