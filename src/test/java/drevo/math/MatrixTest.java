package drevo.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MatrixTest {
 @Test
    void testAdd() {
        Matrix m1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix m2 = new Matrix(new double[][]{{5, 6}, {7, 8}});
        Matrix result = m1.add(m2);
        assertEquals(6, result.get(0, 0));
        assertEquals(8, result.get(0, 1));
        assertEquals(10, result.get(1, 0));
        assertEquals(12, result.get(1, 1));
    }

    @Test
    void testSubtract() {
        Matrix m1 = new Matrix(new double[][]{{5, 6}, {7, 8}});
        Matrix m2 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix result = m1.subtract(m2);
        assertEquals(4, result.get(0, 0));
        assertEquals(4, result.get(0, 1));
        assertEquals(4, result.get(1, 0));
        assertEquals(4, result.get(1, 1));
    }

    @Test
    void testMultiply() {
        Matrix m1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix m2 = new Matrix(new double[][]{{2, 0}, {1, 2}});
        Matrix result = m1.multiply(m2);
        assertEquals(4, result.get(0, 0));
        assertEquals(4, result.get(0, 1));
        assertEquals(10, result.get(1, 0));
        assertEquals(8, result.get(1, 1));
    }

    @Test
    void testScalarMultiply() {
        Matrix m1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix result = m1.scalarMultiply(2);
        assertEquals(2, result.get(0, 0));
        assertEquals(4, result.get(0, 1));
        assertEquals(6, result.get(1, 0));
        assertEquals(8, result.get(1, 1));
    }

    @Test
    void testInvalidAddition() {
        Matrix m1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix m2 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        assertThrows(IllegalArgumentException.class, () -> m1.add(m2));
    }

    @Test
    void testInvalidSubtraction() {
        Matrix m1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix m2 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        assertThrows(IllegalArgumentException.class, () -> m1.subtract(m2));
    }

    @Test
    void testInvalidMultiplication() {
        Matrix m1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix m2 = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        assertThrows(IllegalArgumentException.class, () -> m1.multiply(m2));
    }
}
