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

public class WhiteNoiseFunction implements AnnealingFunction {
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private double x;
    private double noiseLevel;

    public WhiteNoiseFunction(double noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public double noiseLevel() {
        return noiseLevel;
    }

    @Override
    public double compute() {
        return x;
    }

    @Override
    public void reconfigure() {
        x = (rnd.nextInt() % 2 == 0 ? noiseLevel : -noiseLevel) * rnd.nextDouble();
    }

    @Override
    public void assign(AnnealingFunction f) {
        if(f instanceof WhiteNoiseFunction whiteNoise) {
            x = whiteNoise.x;
            noiseLevel = whiteNoise.noiseLevel;
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public AnnealingFunction copy() {
        WhiteNoiseFunction clone = new WhiteNoiseFunction(this.noiseLevel);

        clone.x = this.x;

        return clone;
    }
}
