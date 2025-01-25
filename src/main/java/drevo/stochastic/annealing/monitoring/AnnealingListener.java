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
