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
package drevo.stochastic.annealing.function;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import drevo.stochastic.annealing.AnnealingFunction;

public class InverseSciExponentFunction implements AnnealingFunction{
    Logger logger = LoggerFactory.getLogger(getClass());

    private double x;
    private double delta;
    private double threshold;
    private double maxSteps;
    private double steps;

    public InverseSciExponentFunction(double threshold, double maxSteps) {
        this.x = 0.0;
        this.threshold = threshold;
        this.maxSteps = maxSteps;
        this.steps = 0;

        this.delta = -Math.log(this.threshold) / this.maxSteps;
    }

    public double x() {
        return x;
    }
    
    public double delta() {
        return delta;
    }

    public double threshold() {
        return threshold;
    }

    public double maxSteps() {
        return maxSteps;
    }

    public double steps() {
        return steps;
    }

    @Override
    public double compute() {
        double y = Math.exp(-x);

        logger.info(() -> String.format("steps: %.1f, x: %e, f(x) = %e", steps, x, y));

        return y;
    }

    @Override
    public void reconfigure() {
        steps++;
        x += delta;
    }

    @Override
    public void assign(AnnealingFunction f) {
        if(f instanceof InverseSciExponentFunction inverse) {
            this.x = inverse.x;
            this.delta = inverse.delta;
            this.threshold = inverse.threshold;
            this.maxSteps = inverse.maxSteps;
            this.steps = inverse.steps;
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public AnnealingFunction copy() {
        InverseSciExponentFunction result = new InverseSciExponentFunction(threshold, maxSteps);

        result.x = this.x;
        result.delta = this.delta;
        result.steps = this.steps;

        return result;
    }

    @Override
    public String toString() {
        return "InverseSciExponentFunction [x=" + x + ", delta=" + delta + ", threshold=" + threshold + ", maxSteps="
                + maxSteps + ", steps=" + steps + "]";
    }
}
