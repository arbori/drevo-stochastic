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
package drevo.stochastic.annealing.monitoring;

import java.util.ArrayList;
import java.util.List;

public class AnnealingListener implements Runnable {
    private List<AnnealingState> states = new ArrayList<>();
    private boolean[] finish = { false };
    private final StateChangeHandler handler;
    
    public AnnealingListener(StateChangeHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        while (true) {
            AnnealingState state = null;

            synchronized (finish) {
                synchronized (states) {
                    if (states.isEmpty() && finish[0]) {
                        return;
                    }

                    if (!states.isEmpty()) {
                        state = states.remove(0);
                    }
                }
            }

            if (state != null && handler != null) {
                handler.handleStateChange(state);
            }
        }
    }
    
    public void onStateChange(AnnealingState state) {
        synchronized (states) {        
            states.add(state);
        }
    }
    
    public void finish() {
        synchronized (finish) {
            finish[0] = true;
        }
    }
}
