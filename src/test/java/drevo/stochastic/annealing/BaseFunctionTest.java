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

import drevo.stochastic.ProblemType;
import drevo.stochastic.state.StateChangeHandler;

public abstract class BaseFunctionTest {
    // Configure Annealing Context for minimize
    AnnealingContext minimizeAnnealingContext = new AnnealingContext(
        10000, 0.1, 0.01, 150000, 300, -1, 300, ProblemType.MINIMIZE);

    // Configure Annealing Context for maximize
    AnnealingContext maximizeAnnealingContext = new AnnealingContext(
        10000, 0.1, 0.01, 150000, 300, -1, 300, ProblemType.MAXIMIZE);

    // Configure default Annealing Context for minimize
    AnnealingContext minimizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MINIMIZE);

    // Configure default Annealing Context for maximize
    AnnealingContext maximizeDefaultAnnealingContext = new AnnealingContext(ProblemType.MAXIMIZE);

    StateChangeHandler handler = state -> {
        if(state instanceof AnnealingState) {
            AnnealingState annealingState = (AnnealingState) state;
            
            if (annealingState.currentStep() == 0 && annealingState.temperature() == 0.0) {
                System.out.println(annealingState.message());
            } else {
                System.out.print(annealingState.temperature());
                System.out.print("\t");
                System.out.print(annealingState.currentStep());
                System.out.print("\t");
                System.out.print(annealingState.context().problemType().valueOf() * annealingState.initialEnergy());
                System.out.print("\t");
                System.out.println(annealingState.context().problemType().valueOf() * annealingState.finalEnergy());
            }
        } 
    };
}
