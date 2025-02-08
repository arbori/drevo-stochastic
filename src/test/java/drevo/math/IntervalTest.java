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
package drevo.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

    @Test
    void testConstructorValidBounds() {
        Interval interval = new Interval(1.0, 5.0, 3.0);
        assertEquals(1.0, interval.low());
        assertEquals(5.0, interval.upper());
        assertEquals(3.0, interval.value());
    }

    @Test
    void testConstructorInvalidBounds() {
        assertThrows(IllegalArgumentException.class, () -> new Interval(5.0, 1.0, 3.0));
    }

    @Test
    void testvalueWithinBounds() {
        Interval interval = new Interval(1.0, 5.0, 3.0);
        interval.value(4.0);
        assertEquals(4.0, interval.value());
    }

    @Test
    void testAdd() {
        Interval a = new Interval(1.0, 3.0, 2.0);
        Interval b = new Interval(2.0, 4.0, 3.0);
        Interval result = a.add(b);
        assertEquals(3.0, result.low());
        assertEquals(7.0, result.upper());
        assertEquals(5.0, result.value());
    }

    @Test
    void testSubtract() {
        Interval a = new Interval(1.0, 3.0, 2.0);
        Interval b = new Interval(2.0, 4.0, 3.0);
        Interval result = a.subtract(b);
        assertEquals(-3.0, result.low());
        assertEquals(1.0, result.upper());
        assertEquals(-1.0, result.value());
    }

    @Test
    void testMultiply() {
        Interval a = new Interval(1.0, 3.0, 2.0);
        Interval b = new Interval(2.0, 4.0, 3.0);
        Interval result = a.multiply(b);
        assertEquals(2.0, result.low());
        assertEquals(12.0, result.upper());
        assertEquals(6.0, result.value());
    }

    @Test
    void testDivide() {
        Interval a = new Interval(1.0, 3.0, 2.0);
        Interval b = new Interval(2.0, 4.0, 3.0);
        Interval result = a.divide(b);
        assertEquals(0.25, result.low());
        assertEquals(1.5, result.upper());
        assertEquals(2.0 / 3.0, result.value());
    }

    @Test
    void testDivideByZeroContainingInterval() {
        Interval a = new Interval(1.0, 3.0, 2.0);
        Interval b = new Interval(-1.0, 1.0, 0.0);
        assertThrows(ArithmeticException.class, () -> a.divide(b));
    }
}