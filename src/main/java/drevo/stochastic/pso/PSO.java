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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.IntStream;

import drevo.stochastic.state.StateChangeHandler;
import drevo.stochastic.state.StateChangeListener;

public class PSO<T extends Particle<T>> {
    // Context data.
    private final int maxIterations;
    private final double inertiaWeight;
    private final double cognitiveWeight;
    private final double socialWeight;

    private final Function<T, Double> fitnessFunction;
    private final StateChangeListener listener;
    private final Thread listenerThread;
    private final List<T> swarm;

    private final Random random;
    
    private T globalBest;
    private double globalBestFitness = Double.MAX_VALUE;
    private Semaphore globalBestMutex = new Semaphore(1, true);

    /**
     * Constructs a PSO instance with the specified context, fitness function, state change handler, and initial swarm.
     *
     * @param context the PSO context containing configuration parameters
     * @param fitnessFunction the function to evaluate the fitness of particles
     * @param handler the state change handler to process state changes
     * @param initialSwarm the initial swarm of particles
     */
    public PSO(PSOContext context, Function<T, Double> fitnessFunction, StateChangeHandler handler, List<T> initialSwarm) {
        this.maxIterations = context.maxIterations();
        this.inertiaWeight = context.inertiaWeight();
        this.cognitiveWeight = context.cognitiveWeight();
        this.socialWeight = context.socialWeight();

        this.fitnessFunction = fitnessFunction;
        this.listener = new StateChangeListener(handler);
        this.swarm = new ArrayList<>(initialSwarm);

        this.random = new Random();
       
        listenerThread = new Thread(listener);
        listenerThread.start();

        initializeSwarm();
    }

    /**
     * Initializes the swarm by evaluating each particle's fitness and setting their personal bests.
     * Also determines the global best particle based on fitness.
     */
    private void initializeSwarm() {
        globalBest = swarm.get(0).copy();

        listener.onStateChange(new PSOState(0, 0.0, 0.0, 
            String.format("Initialize swarm with %d particles...", swarm.size())));

        for (T particle : swarm) {
            double fitness = fitnessFunction.apply(particle);
            particle.setPersonalBest(particle.copy());
            particle.setPersonalBestFitness(fitness);
            
            if (fitness < globalBestFitness) {
                globalBestFitness = fitness;
                globalBest = particle.copy();
            }
        }
    }

    /**
     * Starts the PSO optimization process.
     * Iteratively updates particle velocities and positions, evaluates fitness, and updates personal and global bests.
     */
    public void optimize() {
        listener.onStateChange(new PSOState(0, 0.0, 0.0, 
            String.format("Initialize swarm with %.5f global best value.", globalBestFitness)));

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            double lastGlobalBestFitness = globalBestFitness;

            particlesDynamic();
            
            // Optional: Print progress
            if (globalBestFitness - lastGlobalBestFitness < 0.0) {
                double improvement = (globalBestFitness / lastGlobalBestFitness) - 1.0;

                listener.onStateChange(new PSOState(iteration, globalBestFitness, lastGlobalBestFitness, 
                    String.format("Improvement: %f%%", 100.0*improvement)));
            }
        }

        try {
            listener.finish();

            // Wait for the listener thread to finish processing
            listenerThread.join();
        } catch (InterruptedException e) {
            listenerThread.interrupt();
        }
    }

    /**
     * Updates the particles' velocities and positions, evaluates their fitness, and updates personal and global bests.
     */
    private void particlesDynamic() {
        // Parallel processing of particles
        IntStream.range(0, swarm.size()).parallel().forEach(index -> {
            T particle = swarm.get(index);
            
            // Update velocity
            updateVelocity(particle);
            
            // Update position
            particle.updatePosition();
            
            // Evaluate new position
            double currentFitness = fitnessFunction.apply(particle);
            
            // Update personal best if needed
            if (currentFitness < particle.getPersonalBestFitness()) {
                particle.setPersonalBest(particle);
                particle.setPersonalBestFitness(currentFitness);
                
                // Update global best if needed
                if (currentFitness < globalBestFitness) {
                    try {
                        globalBestMutex.acquire();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    globalBestFitness = currentFitness;
                    globalBest.assing(particle);

                    globalBestMutex.release();
                }
            }
        });
    }

    /**
     * Updates the velocity of a particle based on its personal best and the global best.
     *
     * @param particle the particle whose velocity is to be updated
     */
    private void updateVelocity(T particle) {
        // Get references for cleaner code
        T personalBest = particle.getPersonalBest();
        T currentPosition = particle;
        
        // Calculate cognitive and social components
        double[] cognitiveComponent = particle.calculateComponent(
            personalBest, currentPosition, cognitiveWeight, random);
        double[] socialComponent = particle.calculateComponent(
            globalBest, currentPosition, socialWeight, random);
        
        // Update velocity for each dimension
        double[] newVelocity = new double[particle.getVelocity().length];
        for (int i = 0; i < newVelocity.length; i++) {
            newVelocity[i] = inertiaWeight * particle.getVelocity()[i] 
                          + cognitiveComponent[i] 
                          + socialComponent[i];
        }
        
        particle.setVelocity(newVelocity);
    }

    public T getGlobalBest() {
        return globalBest;
    }

    public double getGlobalBestFitness() {
        return globalBestFitness;
    }
}
