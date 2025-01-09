package drevo.stochastic.annealing;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class AnnealingContextTest {
    @Test
    void testContextPositiveTemperatures() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(-100, 20, 0.01, 1000, 100, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, -20, 0.01, 1000, 100, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(-100, -20, 0.01, 1000, 100, ProblemType.MINIMIZE));
    }

    @Test
    void testContextTemperatureRange() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(10, 20, 0.01, 1000, 100, ProblemType.MINIMIZE));
    }

    @Test
    void testContextNonNullPositiveDeadlineSeconds() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, 1000, -100, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, 1000, 0, ProblemType.MINIMIZE));
    }

    @Test
    void testContextNonNullPositiveStepsValue() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, -1000, 100, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, 0, 100, ProblemType.MINIMIZE));
    }

    @Test
    void testContextCoolingRateRange() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, -0.01, 1000, 100, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.51, 1000, 100, ProblemType.MINIMIZE));
    }
}
