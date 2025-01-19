package drevo.stochastic.annealing.monitoring;

import java.util.ArrayList;
import java.util.List;

public abstract class AnnealingListener implements Runnable {
    private List<AnnealingState> states = new ArrayList<>();

    private boolean[] finish = { false };
    
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

            if (state != null) handleStateChange(state);
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

    protected abstract void handleStateChange(AnnealingState state);
}
