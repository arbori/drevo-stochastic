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

import drevo.stochastic.annealing.AnnealingFunction;

public class PlateauFunction implements AnnealingFunction {
    private double x = Math.random() * 20 - 10;

    public double x() {
        return x;
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double value) {
        if(Math.abs(value) < 1) return 0.0;
        
        return Math.abs(value) - 1;
    }

    @Override
    public void reconfigure() {
        x = Math.random() * 20 - 10; // Adjust x randomly within [-10, 10]
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof PlateauFunction) {
            this.x = ((PlateauFunction) f).x;
        }
    }

    @Override
    public boolean isValid() {
        return -10.0 <= x && x <= 10; // Valid domain: [-10, 10]
    }

    @Override
    public AnnealingFunction copy() {
        PlateauFunction clone = new PlateauFunction();
        clone.x = this.x;
        return clone;
    }
}
