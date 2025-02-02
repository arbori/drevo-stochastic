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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import drevo.stochastic.annealing.function.KnapsackFunction;

class MixedIntegerFunctionTest extends BaseFunctionTest {
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private double maximumValue(List<Double> v, List<Double> w, double restriction) {
        if(v.size() != w.size()) {
            throw new IllegalArgumentException("Dimention of values, weight and x's values must be equals");
        }

        int dimention = v.size();

        List<Integer> x = new ArrayList<>();
        for(int i = 0; i < dimention; i++) {
            x.add(0);
        }

        double sumXV;
        double maxXV = Double.NEGATIVE_INFINITY;
        double sumPenalty;

        while(x.stream().mapToInt(n -> n).sum() < x.size()) {        
            sumXV = 0.0;
            sumPenalty = 0.0;

            for(int i = 0; i < x.size(); i++) {
                sumXV += v.get(i) * x.get(i);
                sumPenalty += w.get(i) * x.get(i);
            }

            if(sumPenalty < restriction && maxXV < sumXV) {
                maxXV = sumXV;
            }

            incrementBinaryList(x);
        }

        return maxXV;
    }

    // Function to increment the binary list
    private void incrementBinaryList(List<Integer> binaryList) {
        int n = binaryList.size();

        for (int i = 0; i < n; i++) {
            if (binaryList.get(i) == 0) {
                binaryList.set(i, 1);
                return; // Stop once there's no carry
            } else {
                binaryList.set(i, 0); // Carry over
            }
        }
    }

    @Test
    void maximumBasicTest() {
        List<Double> v = Arrays.asList(20.0, 15.0, 10.0);
        List<Double> w = Arrays.asList(5.0, 8.0, 3.0);
        double penalty = 100.0;
        double restrition = 10.0;

        KnapsackFunction knapsackFunction = new KnapsackFunction(v, w, penalty, restrition);

        double expectedResult = 30.0;
        double result = knapsackFunction.compute(Arrays.asList(1, 0, 1));

        assertEquals(expectedResult, result);

        expectedResult = -265.0;
        result = knapsackFunction.compute(Arrays.asList(1, 1, 0));

        assertEquals(expectedResult, result);
    }

    @Test
    void maximumOptimumTest() {
        List<Double> v = new ArrayList<>();
        List<Double> w = new ArrayList<>();
        double penalty = 100.0;
        double restrition = 10.0;

        int dimention = rnd.nextInt(24);

        for(int i = 0; i < dimention; i++) {
            v.add(rnd.nextDouble(30));
            w.add(rnd.nextDouble(10));
        }

        KnapsackFunction function = new KnapsackFunction(v, w, penalty, restrition);

        // Run Simulated Annealing
        KnapsackFunction result = (KnapsackFunction) SimulatedAnnealing.optimize(
            maximizeAnnealingContext, 
            function, 
            handler);

        double expectedResult = maximumValue(v, w, restrition);

        assertEquals(expectedResult, result.compute());
    }
}
