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

import drevo.stochastic.ProblemType;
import drevo.stochastic.annealing.function.WhiteNoiseFunction;
import drevo.stochastic.annealing.monitoring.AnnealingState;
import drevo.stochastic.annealing.monitoring.StateChangeHandler;

class CountListner implements StateChangeHandler {
    private long amount;

    public long amount() {
        return amount;
    }

    @Override
    public void handleStateChange(AnnealingState state) {
        amount++;

        if (state.currentStep() == 0 && state.temperature() == 0.0) {
            System.out.println(state.message());
        } else {
            System.out.print(state.temperature());
            System.out.print("\t");
            System.out.print(state.currentStep());
            System.out.print("\t");
            System.out.print(state.context().problemType().valueOf() * state.initialEnergy());
            System.out.print("\t");
            System.out.println(state.context().problemType().valueOf() * state.finalEnergy());
        }
    }

}

class EarlyStopTest {
    @Test
    void earlyStopMaximizeTest() {
        double noiseLevel = 1e-3;

        double initialTemperature = 10000;
        double finalTemperature = 0.1;
        double coolingRate = 0.01;
        int steps = 150000;
        long deadline = 300;
        double variationThreshold = noiseLevel;
        int variationPersitence = 5;
        ProblemType problemType = ProblemType.MAXIMIZE;

        WhiteNoiseFunction function = new WhiteNoiseFunction(noiseLevel);

        AnnealingContext context = new AnnealingContext(
            initialTemperature, finalTemperature, coolingRate, steps, deadline, variationThreshold, variationPersitence, problemType);

        CountListner listner = new CountListner();

        SimulatedAnnealing.optimize(context, function, listner);

        assertTrue(listner.amount() <= context.variationPersitence);
    }

    @Test
    void earlyStopMinimizeTest() {
        double noiseLevel = 1e-3;

        double initialTemperature = 10000;
        double finalTemperature = 0.1;
        double coolingRate = 0.01;
        int steps = 150000;
        long deadline = 300;
        double variationThreshold = noiseLevel;
        int variationPersitence = 5;
        ProblemType problemType = ProblemType.MINIMIZE;

        WhiteNoiseFunction function = new WhiteNoiseFunction(noiseLevel);

        AnnealingContext context = new AnnealingContext(
            initialTemperature, finalTemperature, coolingRate, steps, deadline, variationThreshold, variationPersitence, problemType);

        CountListner listner = new CountListner();

        SimulatedAnnealing.optimize(context, function, listner);

        assertTrue(listner.amount() <= context.variationPersitence);
    }
}
