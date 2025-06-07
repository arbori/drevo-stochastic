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
