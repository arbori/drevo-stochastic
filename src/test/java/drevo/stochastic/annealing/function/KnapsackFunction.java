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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import drevo.stochastic.annealing.AnnealingFunction;

public class KnapsackFunction implements AnnealingFunction {
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    private int dimention;
    private List<Integer> x;
    private List<Double> v;
    private List<Double> w;
    private double penalty;
    private double restrition;

    public KnapsackFunction(List<Double> v, List<Double> w, double penalty, double restrition) {
        if(v.size() <= 0 || w.size() <= 0) {
            throw new IllegalArgumentException("Dimention of values and weight must be positive");
        }
        if(v.size() != w.size()) {
            throw new IllegalArgumentException("Dimention of values and weight must be equals");
        }

        this.dimention = v.size();
        this.penalty = penalty;
        this.restrition = restrition;

        this.x = new ArrayList<>(this.dimention);
        this.v = new ArrayList<>(this.dimention);
        this.w = new ArrayList<>(this.dimention);

        for(int i = 0; i < this.dimention; i++) {
            this.x.add(rnd.nextInt(1));
        }

        this.v.addAll(v);
        this.w.addAll(w);
    }

    public List<Integer> getX() {
        return x;
    }

    public List<Double> getV() {
        return v;
    }

    public List<Double> getW() {
        return w;
    }

    @Override
    public double compute() {
        return compute(x);
    }

    public double compute(List<Integer> value) {
        double sumXV = 0.0;
        double sumPenalty = 0.0;
        
        for(int i = 0; i < value.size(); i++) {
            sumXV += v.get(i) * value.get(i);
            sumPenalty += w.get(i) * value.get(i);
        }

        sumPenalty = (sumPenalty >= restrition) ? penalty * (sumPenalty - restrition) : 0.0;

        return sumXV - sumPenalty;
    }

    @Override
    public void reconfigure() {
        int size = rnd.nextInt(x.size());
        int idx;

        for(int i = 0; i < size; i++) {
            idx = rnd.nextInt(x.size());
            x.set(idx, 1 - x.get(idx));
        }
    }

    @Override
    public void assign(AnnealingFunction f) {
        if (f instanceof KnapsackFunction knapsackFunction && knapsackFunction.dimention == this.dimention) {
            for(int i = 0; i < dimention; i++) {
                x.set(i, knapsackFunction.x.get(i));
                v.set(i, knapsackFunction.v.get(i));
                w.set(i, knapsackFunction.w.get(i));
            }
        }
    }

    @Override
    public boolean isValid() {
        double sumPenalty = 0.0;
        
        for(int i = 0; i < dimention; i++) {
            sumPenalty += w.get(i) * x.get(i);
        }

        return sumPenalty < this.restrition;
    }

    @Override
    public AnnealingFunction copy() {
        KnapsackFunction clone = new KnapsackFunction(this.v, this.w, this.penalty, this.restrition);
        
        clone.x.clear();

        for(int i = 0; i < this.dimention; i++) {
            clone.x.add(this.x.get(i));
        }

        return clone;
    }
}

