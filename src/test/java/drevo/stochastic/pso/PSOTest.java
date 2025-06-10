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

import org.junit.jupiter.api.Test;

import drevo.stochastic.state.StateChangeHandler;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

class PSOTest {
    private List<DoubleParticle> swarm;
    private Function<DoubleParticle, Double> sphereFunction;

    private StateChangeHandler stateChangeHandler = state -> {
        // Handle state changes if needed, e.g., logging
        System.out.println("State changed: " + state);
    };

    private final Random rand = new Random();
    
    @BeforeEach
    void setUp() {
        // Initialize particles with random positions and velocities
        swarm = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            double[] position = {rand.nextDouble() * 10 - 5, rand.nextDouble() * 10 - 5};
            double[] velocity = {rand.nextDouble() * 0.1 - 0.05, rand.nextDouble() * 0.1 - 0.05};
            swarm.add(new DoubleParticle(position, velocity));
        }
        
        // Define fitness function (minimizing sphere function)
        sphereFunction = particle -> {
            double[] pos = particle.getPosition();
            return pos[0] * pos[0] + pos[1] * pos[1]; // x^2 + y^2
        };
    }

    @Test
    void testPSOOptimization() {
        PSOContext context = new PSOContext(
            1000,    // max iterations
            0.729,    // inertia weight
            1.49445,  // cognitive weight
            1.49445   // social weight
        );

        // Create PSO instance
        PSO<DoubleParticle> pso = new PSO<>(context, sphereFunction, stateChangeHandler, swarm);
        
        // Run optimization
        pso.optimize();
        
        // Get results
        DoubleParticle best = pso.getGlobalBest();
        double bestFitness = pso.getGlobalBestFitness();
        
        // Assert that optimization improved the solution
        assertAll(
            () -> assertNotNull(best, "Best solution should not be null"),
            () -> assertTrue(bestFitness >= 0, "Fitness should be positive"),
            () -> {
                // Verify that we found a reasonably good solution
                // The sphere function minimum is 0 at (0,0)
                assertTrue(bestFitness < 0.1, 
                    "Should find solution close to optimum (fitness < 0.1)");
            },
            () -> {
                double[] position = best.getPosition();
                assertEquals(2, position.length, "Position should be 2D");
                assertTrue(Math.abs(position[0]) < 0.5, 
                    "X coordinate should be close to 0");
                assertTrue(Math.abs(position[1]) < 0.5,
                    "Y coordinate should be close to 0");
            }
        );
        
        // Additional diagnostic output
        System.out.println("Best solution found:");
        System.out.printf("x = %.5f, y = %.5f%n", 
            best.getPosition()[0], best.getPosition()[1]);
        System.out.println("Fitness: " + bestFitness);
    }

    @Test
    void testPSOWithZeroIterations() {
        assertThrows(IllegalArgumentException.class, () -> new PSOContext(
            0,       // No iterations
            0.729,
            1.49445,
            1.49445)
        );
    }

    @Test
    void testPSOWithOneParticle() {
        List<DoubleParticle> singleSwarm = new ArrayList<>();
        double[] position = {1.0, 1.0};
        double[] velocity = {0.0, 0.0};
        singleSwarm.add(new DoubleParticle(position, velocity));
        
        PSOContext context = new PSOContext(
            100,
            0.729,
            1.49445,
            1.49445
        );

        PSO<DoubleParticle> pso = new PSO<>(context, sphereFunction, stateChangeHandler, swarm);

        pso.optimize();
        
        // With one particle, it should at least find personal best
        assertTrue(pso.getGlobalBestFitness() <= 2.0, 
            "Initial fitness was 1^2 + 1^2 = 2.0");
    }
}
