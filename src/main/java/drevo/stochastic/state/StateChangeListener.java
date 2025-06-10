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
package drevo.stochastic.state;

import java.util.ArrayList;
import java.util.List;

/**
 * A listener that processes state changes in a separate thread.
 * It collects state changes and passes them to a handler for processing.
 */
public class StateChangeListener implements Runnable {
    private List<StateChange> states = new ArrayList<>();
    private boolean[] finish = { false };
    private final StateChangeHandler handler;
    
    public StateChangeListener(StateChangeHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        while (true) {
            StateChange state = null;

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
   
    /**
     * Adds a state change to the listener for processing.
     * 
     * @param state The state change to be processed.
     */
    public void onStateChange(StateChange state) {
        synchronized (states) {        
            states.add(state);
        }
    }
    
    /**
     * Signals that the listener should finish processing.
     * This will cause the run method to exit when there are no more states to process.
     */
    public void finish() {
        synchronized (finish) {
            finish[0] = true;
        }
    }
}
