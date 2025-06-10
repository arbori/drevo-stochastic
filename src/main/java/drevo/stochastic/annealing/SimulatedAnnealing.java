/*
 * Copyright (C) 2024 Marcelo Arbori Nogueira - marcelo.arbori@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package drevo.stochastic.annealing;

import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.ProblemType;
import drevo.stochastic.annealing.monitoring.AnnealingState;
import drevo.stochastic.state.StateChangeListener;
import drevo.stochastic.state.StateChangeHandler;

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

    private final AnnealingContext ctx;
    private final AnnealingFunction function;

    private final StateChangeListener listener;
    private final Thread listenerThread;

    private final ThreadLocalRandom rand;

    private AnnealingFunction best;
    private AnnealingFunction last;
    private double initialEnergy;
    private double finalEnergy;
    private double delta;
    private double probability;
    private double bestValue;
    private int persitenceCount;
    private long endTime;
    private boolean earlyStop;

    /**
     * Create an SimulateAnnealing object to be used in the scope of optimization,
     * or better saying, search process represented by cooling process. The state 
     * of an SimulateAnnealing object is used during the cooling process for 
     * simplify the code.
     * 
     * @param ctx The context of the process was called.
     * @param function The function using to find the optimum value.
     * @param handler If the caller need know the information of the process, it need define a handle for AnnealingState.
     */
    private SimulatedAnnealing(AnnealingContext ctx, AnnealingFunction function, StateChangeHandler handler) {
        this.ctx = ctx;
        this.function = function;

        listener = new StateChangeListener(handler);
        
        listenerThread = new Thread(listener);
        listenerThread.start();

        rand = ThreadLocalRandom.current();

        best = function.copy();
        last = function.copy();
        initialEnergy = 0;
        finalEnergy = 0;
        delta = 0;
        probability = 0;
        bestValue = best.compute();
        persitenceCount = 0;
        endTime = 0;
        earlyStop = false;
    }

    /**
     * Call the search process without a handle for internal state changes and return the better value founded.
     * 
     * @param ctx The context of the process was called.
     * @param function The function using to find the optimum value.
     * @return The better value founded during the process.
     */
    public static AnnealingFunction optimize(AnnealingContext ctx, AnnealingFunction function) {
        return optimize(ctx, function, null);
    }

    /**
     * Call the search process with a handle for internal state changes and return the better value founded.
     * 
     * @param ctx The context of the process was called.
     * @param function The function using to find the optimum value.
     * @param handler
     * @return The better value founded during the process.
     */
    public static AnnealingFunction optimize(AnnealingContext ctx, AnnealingFunction function, StateChangeHandler handler) {
        SimulatedAnnealing sa = new SimulatedAnnealing(ctx, function, handler);

        sa.listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, 0, sa.ctx, 0, false, 
            String.format("Start cooling process with context: %s", sa.ctx)));

        if(!sa.function.isValid()) {
            sa.listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, 0, sa.ctx, 0, false, 
                "The solution candidate sent to cooling process is invalid."));

            return sa.function.copy();
        }

        sa.listener.onStateChange(new AnnealingState(0, sa.initialEnergy, sa.finalEnergy, sa.delta, sa.probability, sa.bestValue, sa.ctx, 0, false, 
            String.format("Start with value: %f", sa.best.compute())));

        // Calculate the deadline
        sa.endTime = System.currentTimeMillis() + sa.ctx.deadline();

        // Cooling process
        for (double temperature = sa.ctx.initialTemperature();
            !sa.earlyStop && System.currentTimeMillis() < sa.endTime && temperature > sa.ctx.finalTemperature();
             temperature *= (1 - sa.ctx.coolingRate())) {

            sa.initialEnergy = sa.ctx.problemType().valueOf() * sa.best.compute();

            changeSolutionState(sa, temperature);
        }

        if(!sa.best.isValid()) {
            sa.listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, sa.bestValue, sa.ctx, 0, false, 
                "The founded solution in cooling process is invalid."));
        }

        sa.listener.onStateChange(new AnnealingState(0, 0, 0, 0, 0, sa.bestValue, sa.ctx, 0, false, 
            String.format("Finish with value: %f", sa.best.compute())));

        sa.listener.finish();

        try {
            sa.listenerThread.join();
        } catch (InterruptedException e) {
            sa.listenerThread.interrupt();
        }

        return sa.best;
    }

    /**
     * Handle the internal change in the solution candidate and decide if should
     * be the better solution founded until the moment.
     * 
     * @param sa The SimulatedAnnealing object for this call of optimize method.
     * @param temperature The temperature value when try change the solution candidate.
     */
    private static void changeSolutionState(SimulatedAnnealing sa, double temperature) {
        for (int currentStep = sa.ctx.steps(); !sa.earlyStop && currentStep > 0; currentStep--) {
            sa.last.reconfigure();

            // The choose a better solution must be do with a valid solution candidate.
            if(!sa.last.isValid()) {
                continue;
            }

            // Calculate the current energy
            sa.finalEnergy = sa.ctx.problemType().valueOf() * sa.last.compute();

            // Calculate energy change
            sa.delta = sa.finalEnergy - sa.initialEnergy;

            // Calculate Boltzmann probability
            sa.probability = Math.exp((-1 * sa.delta) / (BOLTZMANN_CONSTANT * temperature));

            // Check whether to accept the new configuration
            if ((sa.delta <= 0 || sa.rand.nextDouble() < sa.probability)) {
                if((sa.ctx.problemType == ProblemType.MAXIMIZE && sa.bestValue < sa.ctx.problemType().valueOf() * sa.finalEnergy) || (sa.ctx.problemType == ProblemType.MINIMIZE && sa.bestValue > sa.ctx.problemType().valueOf() * sa.finalEnergy)) {
                    sa.listener.onStateChange(new AnnealingState(temperature, sa.initialEnergy, sa.finalEnergy, sa.delta, sa.probability, sa.bestValue, sa.ctx, currentStep, true, "Accepted configuration"));

                    sa.bestValue = sa.ctx.problemType().valueOf() * sa.finalEnergy;
                    sa.best.assign(sa.last);
                }

                sa.initialEnergy = sa.finalEnergy;
            }

            checkStopEarly(sa, temperature, currentStep);
        }
    }

    /**
     * Check is stop early condition was achived and set the flag properly.
     * 
     * @param sa Hinstance of SimulatedAnnealing.
     * @param temperature Temperature of the process.
     * @param currentStep Current step to try a better configuration
     */
    private static void checkStopEarly(SimulatedAnnealing sa, double temperature, int currentStep) {
        // Compute variation take as reference the best value.
        double variation = Math.abs(sa.ctx.problemType().valueOf() * sa.finalEnergy - sa.bestValue);

        // The early stop condition can be true, if varation is less then threshold...
        if (variation < sa.ctx.variationThreshold) {
            sa.persitenceCount++;

            // ... and the time of it is higher then the limit, 
            /// the amount of time this variation still below the threshold.
            if (sa.persitenceCount >= sa.ctx.variationPersitence) {
                sa.earlyStop = true;
                sa.listener.onStateChange(new AnnealingState(temperature, sa.initialEnergy, sa.finalEnergy, sa.delta, sa.probability, sa.bestValue, sa.ctx, currentStep, true, "Early stop due to variation threshold"));
            }
        }
        // It is important restart count for variation because it is a stochastic
        // process and variation can be above or below threshold until stabilize below.. 
        else {
            sa.persitenceCount = 0; // Reset persistence count if variation is above threshold
        }

        // Check for early stop based on time limit computed in the begining of the process
        long now = System.currentTimeMillis();
        if (now >= sa.endTime) {
            sa.earlyStop = true;
            sa.listener.onStateChange(new AnnealingState(temperature, sa.initialEnergy, sa.finalEnergy, sa.delta, sa.probability, sa.bestValue, sa.ctx, currentStep, true, "Early stop due to time limit"));
        }
    }
}
