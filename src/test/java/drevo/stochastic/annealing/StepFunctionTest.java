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

import drevo.stochastic.annealing.function.StepFunction;

class StepFunctionTest extends BaseFunctionTest {
    @Test
    void minimizeStepFunctionDefaultTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedX = 2.0;
        double expected  = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() > expectedX, String.format("The x value do not minimize step function: expectedX: %.5f, result.x(): %.5f", expectedX, result.x()));
    }

    @Test
    void minimizeStepFunctionTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            handler);

        double expectedX = 2.0;
        double expected  = -1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() > expectedX, String.format("The x value do not minimize step function: expectedX: %.5f, result.x(): %.5f", expectedX, result.x()));
    }

    @Test
    void maximizeStepFunctionDefaultTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            maximizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedX = 1.0;
        double expected  = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() < expectedX, String.format("The x value do not minimize step function: expected: %.5f, result: %.5f", expectedX, result.x()));
    }

    @Test
    void maximizeStepFunctionTest() {
        // Define a simple Function for testing
        StepFunction function = new StepFunction();

        // Run Simulated Annealing
        StepFunction result = (StepFunction) SimulatedAnnealing.optimize(
            maximizeAnnealingContext, 
            function,
            handler);

        double expectedX = 1.0;
        double expected  = 1.0;

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(expected - result.compute()) < 10e-5, String.format("Result should be close to the minimum: expected: %.5f, result: %.5f", expected, result.compute()));
        assertTrue(result.x() < expectedX, String.format("The x value do not minimize step function: expectedX: %.5f, result: %.5f", expectedX, result.x()));
    }
}
