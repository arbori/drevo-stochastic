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

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import drevo.stochastic.ProblemType;

/**
 * Unit test for simple App.
 */
class MultimodalFunctionTest extends BaseFunctionTest {
    private static final Logger logger = LoggerFactory.getLogger(MultimodalFunctionTest.class);

    private static final double PRECISION = 10e-2;

    public double multimodalDerivative(double t) {
        return -5.0*(t - 2)*(t - 2) * Math.sin(5*t - 2) + 2*t * Math.cos(5*t - 2) - 4*Math.cos(5*t - 2);
    }

    class MultimodalFunction implements AnnealingFunction {
        public final Random rnd = ThreadLocalRandom.current();

        public final double minXDomine = -0.54248;
        public final double maxXDomine = +4.48407;
        public final double minX       = -0.26330;
        public final double minY       = -5.04437;
        public final double maxX       = +4.20579;
        public final double maxY       = +4.78743;

        private double x = minXDomine + rnd.nextDouble() * (maxXDomine - minXDomine);
    
        @Override
        public double compute() {
            return compute(x);
        }
        public double compute(double t) {
            return (t-2)*(t-2) * Math.cos(5*t - 2);
        }
    
        @Override
        public void reconfigure() {
            x = rnd.nextDouble() * (maxXDomine - minXDomine) + minXDomine;
        }
    
        @Override
        public void assign(AnnealingFunction f) {
            if (f instanceof MultimodalFunction) {
                this.x = ((MultimodalFunction) f).x;
            }
        }
    
        @Override
        public boolean isValid() {
            return minXDomine <= x && x <= maxXDomine;
        }
    
        @Override
        public AnnealingFunction copy() {
            MultimodalFunction clone = new MultimodalFunction();
            clone.x = this.x;
            return clone;
        }
    }

    @Test
    void minimizeMultimodalFunctionTest() {
        // Define a simple Function for testing
        MultimodalFunction function = new MultimodalFunction();

        logger.info(() -> String.format("Start x value: %.8f -> f(x) = %.8f.", function.x, function.compute()));

        // Run Simulated Annealing
        MultimodalFunction result = (MultimodalFunction) SimulatedAnnealing.optimize(
            new AnnealingContext(100000, 0.001, 0.01, 200_000, 30 * 60 * 60, 1, 300, ProblemType.MINIMIZE), 
            function,
            handler);

        logger.info(() -> String.format("Final x value: %.8f -> f(x) = %.8f.", result.x, result.compute()));

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(result.minX - result.x) < PRECISION, String.format("The x value didn't minimize function -> x = %.8f and f(x) = %.8f.", 
            result.x, result.compute()));
        assertTrue(multimodalDerivative(result.x) < PRECISION, String.format("The derivative is not 0 for x = %.8f -> f'(x) = %.8f.", 
            result.x, multimodalDerivative(result.x)));
    }

    @Test
    void maximizeMultimodalFunctionTest() {
        // Define a simple Function for testing
        MultimodalFunction function = new MultimodalFunction();

        logger.info(() -> String.format("Start x value: %.8f -> f(x) = %.8f.", function.x, function.compute()));

        // Run Simulated Annealing
        MultimodalFunction result = (MultimodalFunction) SimulatedAnnealing.optimize(
            new AnnealingContext(100000, 0.001, 0.01, 200_000, 30 * 60 * 60, 1, 300, ProblemType.MAXIMIZE), 
            function,
            handler);

        logger.info(() -> String.format("Final x value: %.8f -> f(x) = %.8f.", result.x, result.compute()));

        // Assert that we found a reasonable solution
        assertTrue(Math.abs(result.maxX - result.x) < PRECISION, String.format("The x value didn't maximize function -> x = %.8f and f(x) = %.8f.", 
            result.x, result.compute()));
        assertTrue(multimodalDerivative(result.x) < PRECISION, String.format("The derivative is not 0 for x = %.8f -> f'(x) = %.8f.", 
            result.x, multimodalDerivative(result.x)));
    }
}
