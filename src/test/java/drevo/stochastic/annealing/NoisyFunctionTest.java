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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.QuadraticNoiseFunction;

class NoisyFunctionTest extends BaseFunctionTest {
    @Test
    void minimizeQuadraticNoiseFunctionDefaultTest() {
        // Define a simple Function for testing
        QuadraticNoiseFunction function = new QuadraticNoiseFunction();

        // Run Simulated Annealing
        QuadraticNoiseFunction result = (QuadraticNoiseFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(expectedMin < resultY && resultY < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, resultY));
        assertTrue(expectedMinX < result.x() && result.x() < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, result.x()));
    }

    @Test
    void minimizeQuadraticNoiseFunctionTest() {
        // Define a simple Function for testing
        QuadraticNoiseFunction function = new QuadraticNoiseFunction();

        // Run Simulated Annealing
        QuadraticNoiseFunction result = (QuadraticNoiseFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            handler);

        double expectedMinX = 2.0 - function.noise;
        double expectedMaxX = 2.0 + function.noise;
        double expectedMin  = -function.noise;
        double expectedMax  = +function.noise;

        // Assert that we found a reasonable solution
        double resultY = result.compute();
        assertTrue(expectedMin < resultY && resultY < expectedMax, 
            String.format("Result should be close to the minimum - expected: [%.5f, %.5f], result: %.5f", expectedMinX, expectedMaxX, result.x()));
        assertTrue(expectedMinX < result.x() && result.x() < expectedMaxX, 
            String.format("The x value do not minimize (x - 2)^2 + noise - expectedX: [%.5f, %.5f], x: %.5f", expectedMinX, expectedMaxX, result.x()));
    }
}
