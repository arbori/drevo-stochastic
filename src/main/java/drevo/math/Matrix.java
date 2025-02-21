package drevo.math;

public class Matrix {
    private double[][] data;

    public final int rows;
    public final int cols;

    // Constructor to create a matrix with given dimensions
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[this.rows][this.cols];
    }

    // Constructor to create a matrix from a 2D array
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;

        this.data = new double[this.rows][this.cols];

        for (int i = 0; i < this.rows; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, this.cols);
        }
    }

    // Get the value at a specific position
    public double get(int row, int col) {
        return data[row][col];
    }

    // Set the value at a specific position
    public void set(int row, int col, double value) {
        data[row][col] = value;
    }

    // Add two matrices
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition.");
        }

        Matrix result = new Matrix(rows, cols);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] + other.data[i][j];
            }
        }

        return result;
    }

    // Subtract two matrices
    public Matrix subtract(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for subtraction.");
        }

        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] - other.data[i][j];
            }
        }

        return result;
    }

    // Multiply two matrices
    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Number of columns in the first matrix must equal the number of rows in the second matrix for multiplication.");
        }

        Matrix result = new Matrix(this.rows, other.cols);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    result.data[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }

        return result;
    }

    // Multiply matrix by a scalar
    public Matrix scalarMultiply(double scalar) {
        Matrix result = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j] * scalar;
            }
        }

        return result;
    }

    // Override toString for easy printing
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}