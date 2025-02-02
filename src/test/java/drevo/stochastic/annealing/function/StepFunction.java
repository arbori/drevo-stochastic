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

public class StepFunction  implements AnnealingFunction {
    private double x = Math.random() * 10 - 5;

    public double x() {
        return x;
    }

    @Override
    public double compute() {
        return compute(x);
    }
    public double compute(double value) {
        if(value < 1) return 1;
        else if(1 <= value && value <= 2) return 0;
        
        return -1;
    }

    @Override
    public void reconfigure() {
        x = Math.random() * 10 - 5; // Adjust x randomly within [-5, 5]
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof StepFunction) {
            this.x = ((StepFunction) f).x;
        }
    }

    @Override
    public boolean isValid() {
        return -5.0 <= x && x <= 5; // Valid domain: [-10, 10]
    }

    @Override
    public AnnealingFunction copy() {
        StepFunction clone = new StepFunction();
        clone.x = this.x;
        return clone;
    }
}
