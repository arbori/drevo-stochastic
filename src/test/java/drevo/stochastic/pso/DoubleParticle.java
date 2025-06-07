package drevo.stochastic.pso;

import java.util.Random;

public class DoubleParticle implements Particle<DoubleParticle> {
    private double[] position;
    private double[] velocity;
    private DoubleParticle personalBest;
    private double personalBestFitness;
    
    public DoubleParticle(double[] position, double[] velocity) {
        this.position = position.clone();
        this.velocity = velocity.clone();
    }

    @Override
    public double[] getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(double[] velocity) {
        this.velocity = velocity.clone();
    }

    @Override
    public void updatePosition() {
        for (int i = 0; i < position.length; i++) {
            position[i] += velocity[i];
        }
    }

    @Override
    public DoubleParticle copy() {
        return new DoubleParticle(position, velocity);
    }

    @Override
    public void assing(DoubleParticle particle) {
        position = particle.position.clone();
        velocity = particle.velocity.clone();
        personalBest = particle.personalBest.copy();
        personalBestFitness = particle.personalBestFitness;
    }

    @Override
    public DoubleParticle getPersonalBest() {
        return personalBest;
    }

    @Override
    public void setPersonalBest(DoubleParticle personalBest) {
        this.personalBest = personalBest.copy();
    }

    @Override
    public double getPersonalBestFitness() {
        return personalBestFitness;
    }

    @Override
    public void setPersonalBestFitness(double fitness) {
        this.personalBestFitness = fitness;
    }

    @Override
    public double[] calculateComponent(DoubleParticle guide, DoubleParticle current, 
                                      double weight, Random random) {
        double[] component = new double[position.length];
        for (int i = 0; i < component.length; i++) {
            double r = random.nextDouble();
            component[i] = weight * r * (guide.position[i] - current.position[i]);
        }
        return component;
    }
    
    public double[] getPosition() {
        return position;
    }
}
