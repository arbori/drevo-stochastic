package drevo.stochastic.pso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.IntStream;

public class PSO<T extends Particle<T>> {
    private final List<T> swarm;
    private final Function<T, Double> fitnessFunction;
    private final int maxIterations;
    private final double inertiaWeight;
    private final double cognitiveWeight;
    private final double socialWeight;
    private final Random random;
    
    private T globalBest;
    private double globalBestFitness = Double.MAX_VALUE;
    private Semaphore globalBestMutex = new Semaphore(1, true);

    public PSO(List<T> initialSwarm, PSOContext<T> context) {
        this.swarm = new ArrayList<>(initialSwarm);
        this.fitnessFunction = context.fitnessFunction();
        this.maxIterations = context.maxIterations();
        this.inertiaWeight = context.inertiaWeight();
        this.cognitiveWeight = context.cognitiveWeight();
        this.socialWeight = context.socialWeight();
        this.random = new Random();
        
        initializeSwarm();
    }

    private void initializeSwarm() {
        globalBest = swarm.get(0).copy();

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

    public void optimize() {
        double lastGlobalBestFitness = this.globalBestFitness;

        System.out.println("Start: Best fitness = " + -lastGlobalBestFitness);

        for (int i = 0; i < maxIterations; i++) {
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
            
            // Optional: Print progress
            if (globalBestFitness < lastGlobalBestFitness) {
                double improvement = (globalBestFitness / lastGlobalBestFitness) - 1.0;

                lastGlobalBestFitness = globalBestFitness;

                System.out.println(String.format("Iteration %d: Best fitness = %.2f, Improvement: %.4f%%", i, -lastGlobalBestFitness, 100.0*improvement));
            }
        }
    }

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
