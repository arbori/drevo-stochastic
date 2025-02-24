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

import drevo.stochastic.ProblemType;

/**
 * AnnealingContext is a registry with parameters for the functionality of the Simulated Annealing algorithm, they are:
 * <pre>
 * - initialTemperature
 * It is the initial temperature considering the initial configuration of the solution.
 * - finalTemperature
 * It is the final temperature considering the final (with lock the best) configuration of the solution.
 * - coolingRate
 * It is the rate of the system will cooling in the simulation, that mean the simulation of the cooling.
 * - steps
 * For each temperature the algorithm will try find a beter configuration for the number of steps.
 * - deadline
 * It is a control parameter that put a time limit to run the simulation, in milliseconds.
 * - variationThreshold
 * The variationThreshold is the parameter to informe the algorithm that if the value founded is below this threshoul the process can stop.
 * - variationPersitence
 * The algorithm will not stop in the first time variationThreshold is achived, the variationPersitence is about how many interations variationThreshold need be achived to stop algorithm.
 * - problemType
 * Define if to find the problem solution a maximization or minimization is required.
 * </pre>
 * 
 * Together with an implementation of AnnealingFunction, they provide all information needed to run the simulation.
 */
public class AnnealingContext {
    public final double initialTemperature;
    public final double finalTemperature;
    public final double coolingRate;
    public final int steps;
    public final long deadline;
    public final double variationThreshold;
    public final long variationPersitence;
    public final ProblemType problemType;

    public AnnealingContext(ProblemType problemType) {
        this(10000, 0.1, 0.01, 150000, 300, -1, -1, problemType);
    }

    public AnnealingContext(double initialTemperature, double finalTemperature, double coolingRate, int steps, long deadline, double variationThreshold, int variationPersitence, ProblemType problemType) {
        if(initialTemperature < 0.0 || finalTemperature < 0.0) {
            throw new IllegalArgumentException("Temperatures must have positive values.");
        }

        if(initialTemperature < finalTemperature) {
            throw new IllegalArgumentException("The initial temperature must be greater then final one.");
        }

        if(coolingRate <= 0.0 || coolingRate > 0.5) {
            throw new IllegalArgumentException("Cooling rate must have positive values less or equals than 0.5.");
        }

        if(steps <= 0) {
            throw new IllegalArgumentException("Steps must have non null positive values.");
        }

        if(deadline <= 0) {
            throw new IllegalArgumentException("Deadline must have non null positive values.");
        }

        this.initialTemperature = initialTemperature;
        this.finalTemperature = finalTemperature;
        this.coolingRate = coolingRate;
        this.steps = steps;
        this.deadline = deadline;
        this.variationThreshold = variationThreshold; 
        this.variationPersitence = variationPersitence;
        this.problemType = problemType;
    }

    public double initialTemperature() { return initialTemperature; }

    public double finalTemperature() { return finalTemperature; }

    public double coolingRate() { return coolingRate; }

    public int steps() { return steps; }

    public long deadline() { return deadline; }

    public double variationThreshold() { return variationThreshold; }

    public long variationPersitence() { return variationPersitence; }

    public ProblemType problemType() { return problemType; }

    @Override
    public String toString() {
        return "{AnnealingContext: {" + 
                    "'initialTemperature': " + initialTemperature + 
                    ", 'finalTemperature': " + finalTemperature +
                    ", 'coolingRate': '" + coolingRate + 
                    "', 'steps': '" + steps + 
                    "', 'deadline': '" + deadline + 
                    "', 'variationThreshold': '" + variationThreshold +
                    "', 'variationPersitence': '" + variationPersitence +
                    "', 'problemType': '" + problemType + "'" +
                "}";
    }
}
