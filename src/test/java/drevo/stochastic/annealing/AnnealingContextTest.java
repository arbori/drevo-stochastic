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

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import drevo.stochastic.ProblemType;

class AnnealingContextTest {
    @Test
    void testContextPositiveTemperatures() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(-100, 20, 0.01, 1000, 100, 1, 300, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, -20, 0.01, 1000, 100, 1, 300, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(-100, -20, 0.01, 1000, 100, 1, 300, ProblemType.MINIMIZE));
    }

    @Test
    void testContextTemperatureRange() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(10, 20, 0.01, 1000, 100, 1, 300, ProblemType.MINIMIZE));
    }

    @Test
    void testContextNonNullPositiveDeadlineSeconds() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, 1000, -100, 1, 300, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, 1000, 0, 1, 300, ProblemType.MINIMIZE));
    }

    @Test
    void testContextNonNullPositiveStepsValue() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, -1000, 100, 1, 300, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.01, 0, 100, 1, 300, ProblemType.MINIMIZE));
    }

    @Test
    void testContextCoolingRateRange() {
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, -0.01, 1000, 100, 1, 300, ProblemType.MINIMIZE));
        assertThrows(IllegalArgumentException.class, () -> new AnnealingContext(100, 20, 0.51, 1000, 100, 1, 300, ProblemType.MINIMIZE));
    }
}
