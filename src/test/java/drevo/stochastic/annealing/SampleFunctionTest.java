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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.SampleFunction;

class SampleFunctionTest extends BaseFunctionTest {
    private static final int BOUND = 1000;

    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    @Test
    void maximumOptimumDefaultTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(
            maximizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedResult = Double.NEGATIVE_INFINITY;

        int indexMaximum = -1;

        for(int i = 0; i < function.size(); i++) {
            if(expectedResult < function.x(i)) {
                indexMaximum = i;
                expectedResult = function.x(i);
            }
        }

        assertEquals(indexMaximum, result.index(), String.format("indexMaximum: %d => value: %.5f, function.index: %d => value: %.5f", indexMaximum, result.x(indexMaximum), result.index(), result.compute()));
        assertEquals(expectedResult, result.x(), String.format("expectedResult: %f, result: %f", expectedResult, result.x()));
    }

    @Test
    void maximumOptimumTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(
            maximizeAnnealingContext, 
            function,
            handler);

        double expectedResult = Double.NEGATIVE_INFINITY;

        int indexMaximum = -1;

        for(int i = 0; i < function.size(); i++) {
            if(expectedResult < function.x(i)) {
                indexMaximum = i;
                expectedResult = function.x(i);
            }
        }

        assertEquals(indexMaximum, result.index(), String.format("indexMaximum: %d => value: %.5f, function.index: %d => value: %.5f", indexMaximum, result.x(indexMaximum), result.index(), result.compute()));
        assertEquals(expectedResult, result.compute(), String.format("expectedResult: %f, result: %f", expectedResult, result.compute()));
    }

    @Test
    void minimumOptimumDefaultTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(
            minimizeDefaultAnnealingContext, 
            function,
            handler);

        double expectedResult = Double.POSITIVE_INFINITY;

        int indexMinimum = -1;

        for(int i = 0; i < function.size(); i++) {
            if(expectedResult > function.x(i)) {
                indexMinimum = i;
                expectedResult = function.x(i);
            }
        }

        assertEquals(indexMinimum, result.index(), String.format("indexMinimum: %d => value: %.5f, function.index: %d => value: %.5f", indexMinimum, result.x(indexMinimum), result.index(), result.compute()));
        assertEquals(expectedResult, result.compute(), String.format("expectedResult: %f, result: %f", expectedResult, result.compute()));
    }

    @Test
    void minimumOptimumTest() {
        int dimention = rnd.nextInt(BOUND);
        double bound = 50.0;

        SampleFunction function = new SampleFunction(dimention, bound);

        // Run Simulated Annealing
        SampleFunction result = (SampleFunction) SimulatedAnnealing.optimize(
            minimizeAnnealingContext, 
            function,
            handler);

        double expectedResult = Double.POSITIVE_INFINITY;

        int indexMinimum = -1;

        for(int i = 0; i < function.size(); i++) {
            if(expectedResult > function.x(i)) {
                indexMinimum = i;
                expectedResult = function.x(i);
            }
        }

        assertEquals(indexMinimum, result.index(), String.format("indexMinimum: %d => value: %.5f, function.index: %d => value: %.5f", indexMinimum, result.x(indexMinimum), result.index(), result.compute()));
        assertEquals(expectedResult, result.compute(), String.format("expectedResult: %f, result: %f", expectedResult, result.compute()));
    }
}
