package drevo.stochastic.annealing;

import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.ProblemType;

import drevo.stochastic.annealing.monitoring.AnnealingListener;
import drevo.stochastic.annealing.monitoring.AnnealingState;

/**
 * This class is an implementation of Simulated Annealing Algorithm that simulate a cooling process as a metaphor 
 * for the search good solution of a problem.
 * 
 * "Simulated annealing (SA) is a probabilistic technique for approximating the global optimum of a given function. 
 * Specifically, it is a metaheuristic to approximate global optimization in a large search space for an optimization
 *  problem. For large numbers of local optima, SA can find the global optimum.[1] It is often used when the search 
 * space is discrete (for example the traveling salesman problem, the boolean satisfiability problem, protein 
 * structure prediction, and job-shop scheduling). For problems where finding an approximate global optimum is more 
 * important than finding a precise local optimum in a fixed amount of time, simulated annealing may be preferable to 
 * exact algorithms such as gradient descent or branch and bound."
 * ...
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Simulated_annealing">https://en.wikipedia.org/wiki/Simulated_annealing</a>
 * 
 * Let's say that some problem is represented by f(x) = sin(x), this is the model and the objective funcion.
 * The first thing to do to use the SimulatedAnnealing.optimize method is implement the objective function using
 * the interface AnnealingFunction as below.
 * 
 *  class SinObjectiveFunction implements AnnealingFunction {
 *      private double x = 0;
 *  
 *      @Override
 *      public double compute() {
 *          return Math.sin(x);
 *      }
 *  
 *      @Override
 *      public void reconfigure() {
 *          x = Math.random() * 2* Math.PI; // Adjust x randomly within [0, 2pi]
 *      }
 *  
 *      @Override
 *      public void assign(AnnealingFunction f) {
 *          if (f instanceof SinObjectiveFunction) {
 *              this.x = ((SinObjectiveFunction) f).x;
 *          }
 *      }
 *  
 *      @Override
 *      public boolean isValid() {
 *          return 0.0 <= x && x <= 2*Math.PI; // Valid domain: [0, 2pi]
 *      }
 *  
 *      @Override
 *      public AnnealingFunction copy() {
 *          SinObjectiveFunction clone = new SinObjectiveFunction();
 *          clone.x = this.x;
 *          return clone;
 *      }
 *  }
 *
 * The atribute x is the current state of the model, a implementation of AnnealingFunction need have 
 * internaly the representation of the model. The compute() method compute the value of the internal state, 
 * the represents a point in the domine of objective function, in this case sin(x). The reconfigure() method
 * change the internal state trying explore domine space to find a beter solution. The assign(AnnealingFunction f)
 * method set the internal state of the model with the state of other with the same implementation. The isValid()
 * method check if the state is valid, that is, if the point represented by the state is part of domine function.
 * Finally, copy() method create other hinstance.
 * 
 * AnnealingContex in other way, is a class with parameters that will be use in some execution of SimulatedAnnealing.optimize.
 * It provide a constructor with only type of problem definition, if is a maximization or a minimization one, but 
 * it also have a complete constructor to define all context property, both signature are below.
 * 
 * public AnnealingContext(ProblemType problemType) {...}
 * public AnnealingContext(double initialTemperature, double finalTemperature, double coolingRate, int steps, long deadline, ProblemType problemType) {...}
 * 
 * With these two objects, it is enoght to call: SimulatedAnnealing.optimize(AnnealingContext ctx, AnnealingFunction function); In the end of execution 
 * a new AnnealingFunction is returned and its internal state is the solution founded, the optimum point, minimum or maximum, depending on the type of the problem. 
 */
public class SimulatedAnnealing {
    private static final double BOLTZMANN_CONSTANT = 8.6173432e-5;

    private SimulatedAnnealing() {
    }
    
    public static AnnealingFunction optimize(AnnealingContext ctx, AnnealingFunction function, AnnealingListener listener) {
        Thread listenerThread = new Thread(listener);
        listenerThread.start();

        if(listener != null) listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, 0, false, 
            String.format("Start cooling process with context: %s", ctx)));

        if(!function.isValid()) {
            if(listener != null) listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, 0, false, 
                "The solution candidate sent to cooling process is invalid."));

            return function.copy();
        }

        ThreadLocalRandom rand = ThreadLocalRandom.current();

        AnnealingFunction best = function.copy();
        AnnealingFunction last = function.copy();
        double initialEnergy = 0;
        double finalEnergy = 0;
        double delta = 0;
        double probability = 0;
        double bestValue = best.compute();

        if(listener != null) listener.onStateChange(new AnnealingState(0, initialEnergy, finalEnergy, delta, probability, 0, false, 
            String.format("Start with value: %.5f", best.compute())));

        // Calculate the deadline
        long endTime = System.currentTimeMillis() + ctx.deadline() * 1000;

        // Cooling process
        for (double temperature = ctx.initialTemperature();
             System.currentTimeMillis() < endTime && temperature > ctx.finalTemperature();
             temperature *= (1 - ctx.coolingRate())) {

            initialEnergy = ctx.problemType().valueOf() * best.compute();

            for (int currentStep = ctx.steps(); currentStep > 0; currentStep--) {
                last.reconfigure();

                // Calculate the current energy
                finalEnergy = ctx.problemType().valueOf() * last.compute();

                // Calculate energy change
                delta = finalEnergy - initialEnergy;

                // Calculate Boltzmann probability
                probability = Math.exp((-1 * delta) / (BOLTZMANN_CONSTANT * temperature));

                // Check whether to accept the new configuration
                if ((delta <= 0 || rand.nextDouble() < probability) && last.isValid()) {
                    if(listener != null) {
                        listener.onStateChange(new AnnealingState(temperature, initialEnergy, finalEnergy, delta, probability, currentStep, true, "Accepted configuration"));
                    }

                    initialEnergy = finalEnergy;

                    if((ctx.problemType == ProblemType.MAXIMIZE && bestValue < ctx.problemType().valueOf() * finalEnergy) || (ctx.problemType == ProblemType.MINIMIZE && bestValue > ctx.problemType().valueOf() * finalEnergy)) {
                        best.assign(last);
                        bestValue = best.compute();
                    }
                }
                else {
                    if(listener != null) {
                        listener.onStateChange(new AnnealingState(temperature, initialEnergy, finalEnergy, delta, probability, currentStep, true, "Not accepted configuration"));
                    }
                }
            }
        }

        if(!best.isValid() && listener != null) {
            listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, 0, false, 
                "The founded solution in cooling process is invalid."));
        }

        if(listener != null) {
            listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, 0, false, 
               String.format("Finish with value: %.5f", best.compute())));

            listener.finish();
        }

        listener.finish();

        try {
            listenerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return best;
    }
}
