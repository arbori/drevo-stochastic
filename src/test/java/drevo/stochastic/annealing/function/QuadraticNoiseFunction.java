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
package drevo.stochastic.annealing.function;

import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class QuadraticNoiseFunction implements AnnealingFunction {
    ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private double x = rnd.nextDouble() * 20 - 10;
    
    public final double noise = 0.1;

    public double x() {
        return x;
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double value) {
        double sign = rnd.nextInt(100) % 2 == 0 ? 1 : -1;

        return (value - 2.0) * (value - 2.0) + sign*(rnd.nextDouble() * 2*noise - noise);
    }

    @Override
    public void reconfigure() {
        x = Math.random() * 20 - 10; // Adjust x randomly within [-10, 10]
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof QuadraticNoiseFunction) {
            this.x = ((QuadraticNoiseFunction) f).x;
        }
    }

    @Override
    public boolean isValid() {
        return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
    }

    @Override
    public AnnealingFunction copy() {
        QuadraticNoiseFunction clone = new QuadraticNoiseFunction();
        clone.x = this.x;
        return clone;
    }
}
