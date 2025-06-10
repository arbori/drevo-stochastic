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

import java.util.Random;

public interface Particle<T extends Particle<T>> {
    // Get the current velocity of the particle
    double[] getVelocity();
    
    /**
     * Set the velocity of the particle
     * @param velocity the new velocity array
     * @throws IllegalArgumentException if the velocity array does not match the particle's dimension
     * @throws NullPointerException if the velocity array is null
     */
    void setVelocity(double[] velocity);
    
    /**
     * Update the position based on current velocity
     * This method should be called after velocity has been updated.
     */
    void updatePosition();
    
    /**
     *  Get a copy of this particle
     */
    T copy();

    /**
     * Assign a new particle to this particle.
     * @param particle the particle to assign
     * @throws IllegalArgumentException if the particle does not match the type of this particle
     * @throws ClassCastException if the particle is not of the same type
     */
    void assing(T particle);
    
    // Get the personal best position of this particle
    T getPersonalBest();
    
    // Set the personal best position
    void setPersonalBest(T personalBest);
    
    // Get the fitness of the personal best position
    double getPersonalBestFitness();
    
    // Set the fitness of the personal best position
    void setPersonalBestFitness(double fitness);
    
    // Calculate component for velocity update
    double[] calculateComponent(T guide, T current, double weight, Random random);
}
