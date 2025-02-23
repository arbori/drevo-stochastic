/*
 * Copyright (C) 2025 Marcelo Arbori Nogueira - marcelo.arbori@gmail.com
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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import drevo.stochastic.ProblemType;
import drevo.stochastic.annealing.function.InverseSciExponentFunction;

class InverseSciExponentTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void stepTest() {
        InverseSciExponentFunction func = new InverseSciExponentFunction(1e-3, 201);

        double totalSteps = 0.0;

        while (func.compute() > func.threshold()) {
            func.reconfigure();

            totalSteps += 1.0;
        }

        String message = String.format("x: %.5f, f(x): %.5f, %s", func.x(), func.compute(), func.toString());

        assertEquals(totalSteps, func.steps(), message);

        logger.info(() -> message);
    }

    @Test
    void fixedStepsInverseSciExponentTest() {
        double initialTemperature = 10000;
        double finalTemperature   = 0.1;
        double coolingRate        = 0.01;
        int steps                 = 10;
        long deadline             = 1000*60;
        double variationThreshold = 1e-3;
        int variationPersitence   = 200;
        ProblemType problemType   = ProblemType.MINIMIZE;

        InverseSciExponentFunction function = new InverseSciExponentFunction(variationThreshold, variationPersitence);

        // Run Simulated Annealing with the default context
        InverseSciExponentFunction result = (InverseSciExponentFunction) SimulatedAnnealing.optimize(
                new AnnealingContext(initialTemperature, finalTemperature, coolingRate, steps, deadline, variationThreshold, variationPersitence, problemType),
                function,
                null);

        double y = result.compute();

        logger.info(() -> String.format("f(x) = %e, %s", y, result.toString()));
        
        assertTrue(result.steps() <= variationPersitence);
        assertEquals(y, variationThreshold, 1e-15);
    }

    @Test
    void timeWaitingInverseSciExponentTest() {
        double initialTemperature = 10000;
        double finalTemperature   = 0.1;
        double coolingRate        = 0.01;
        int steps                 = 10;
        long deadline             = 1000*1;
        double variationThreshold = -1;
        int variationPersitence   = -1;
        ProblemType problemType   = ProblemType.MINIMIZE;

        InverseSciExponentFunction function = new InverseSciExponentFunction(variationThreshold, variationPersitence);

        long start = System.currentTimeMillis();
        
        InverseSciExponentFunction result = (InverseSciExponentFunction) SimulatedAnnealing.optimize(
                new AnnealingContext(initialTemperature, finalTemperature, coolingRate, steps, deadline, variationThreshold, variationPersitence, problemType),
                function,
                null);

        long finish = System.currentTimeMillis();
        long timePass = finish - start;
        double y = result.compute();

        logger.info(() -> String.format("time: %d, f(x) = %.5f, %s", timePass, y, result.toString()));
        
        assertEquals(deadline, timePass);
    }
}
