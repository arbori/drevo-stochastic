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

public record PSOContext(
    int maxIterations,
    double inertiaWeight,
    double cognitiveWeight,
    double socialWeight
) {
    /**
     * Creates a new PSOContext with the specified parameters.
     *
     * @param maxIterations the maximum number of iterations for the PSO algorithm
     * @param inertiaWeight the weight applied to the particle's previous velocity
     * @param cognitiveWeight the weight applied to the particle's personal best position
     * @param socialWeight the weight applied to the global best position of the swarm
     */
    public PSOContext {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations must be greater than 0");
        }
        if (inertiaWeight < 0 || cognitiveWeight < 0 || socialWeight < 0) {
            throw new IllegalArgumentException("Weights must be non-negative");
        }
    }
}
