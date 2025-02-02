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

import drevo.stochastic.annealing.function.QuadraticFunction;

class UnimodalFunctionsTest extends BaseFunctionTest {
    @Test
    void minimizeQuadraticFunctionDefaultTest() {
        // Define a simple Function for testing
        QuadraticFunction function = new QuadraticFunction();

        // Run Simulated Annealing
        QuadraticFunction result = (QuadraticFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x()) < 10e-2, String.format("The x value do not minimize (x - 2)^2. expectedX: %.5f, result.x: %.5f", expectedX, result.x()));
    }

    @Test
    void minimizeQuadraticFunctionTest() {
        // Define a simple Function for testing
        QuadraticFunction function = new QuadraticFunction();

        // Run Simulated Annealing
        QuadraticFunction result = (QuadraticFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            handler);

        double expectedX = 2.0;
        double expected  = 0.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("It didn't minimize. expected: %.5f, result.compute(): %.5f.", expected, result.compute()));
        assertTrue(Math.abs(expectedX - result.x()) < 10e-2, String.format("The x value do not minimize (x - 2)^2. expectedX: %.5f, result.x: %.5f", expectedX, result.x()));
    }
}
