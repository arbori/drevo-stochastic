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
package drevo.stochastic.pso;

/**
 * Represents the context for a Particle Swarm Optimization (PSO) algorithm.
 * This record encapsulates the parameters required to configure the PSO algorithm.
 */
public class PSOContext {
    final int maxIterations; // Maximum number of iterations for the PSO algorithm
    final double inertiaWeight; // Weight applied to the particle's previous velocity
    final double cognitiveWeight; // Weight applied to the particle's personal best position
    final double socialWeight; // Weight applied to the global best position of the swarm
    final double variationThreshold; // Threshold for variation in particle positions to trigger early stopping
    final long variationPersitence; // Number of iterations a variation must persist to trigger early stopping

    /**
     * Creates a new PSOContext with the specified parameters.
     *
     * @param maxIterations the maximum number of iterations for the PSO algorithm
     * @param inertiaWeight the weight applied to the particle's previous velocity
     * @param cognitiveWeight the weight applied to the particle's personal best position
     * @param socialWeight the weight applied to the global best position of the swarm
     */
    public PSOContext(int maxIterations, double inertiaWeight, double cognitiveWeight, double socialWeight, double variationThreshold, long variationPersitence) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations must be greater than 0");
        }
        if (inertiaWeight < 0 || cognitiveWeight < 0 || socialWeight < 0) {
            throw new IllegalArgumentException("Weights must be non-negative");
        }

        this.maxIterations = maxIterations;
        this.inertiaWeight = inertiaWeight;
        this.cognitiveWeight = cognitiveWeight;
        this.socialWeight = socialWeight;
        this.variationThreshold = variationThreshold;
        this.variationPersitence = variationPersitence;
    }

    /**
     * Creates a new PSOContext with the specified parameters.
     *
     * @param maxIterations the maximum number of iterations for the PSO algorithm
     * @param inertiaWeight the weight applied to the particle's previous velocity
     * @param cognitiveWeight the weight applied to the particle's personal best position
     * @param socialWeight the weight applied to the global best position of the swarm
     */
    public PSOContext(int maxIterations, double inertiaWeight, double cognitiveWeight, double socialWeight) {
        this(maxIterations, inertiaWeight, cognitiveWeight, socialWeight, 0.0, Long.MAX_VALUE);
    }
}
