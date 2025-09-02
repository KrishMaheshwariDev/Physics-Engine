package com.simulation.cloth.core.utils;

public class Matrix2D {
    private double[][] matrix = new double[3][3];

    public Matrix2D() { // Identity matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public Matrix2D(double[][] matrix) { // Copy the other matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    public Matrix2D(double col1[], double col2[], double col3[]) {
        for (int i = 0; i < 3; i++) {
            matrix[i][0] = col1[i];
            matrix[i][1] = col2[i];
            matrix[i][2] = col3[i];
        }
    }

    // Add and Subtract

    public double[][] add(Matrix2D other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return result;
    }

    public double[][] subtract(Matrix2D other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return result;
    }

    // Multiplication

    public double[][] multiplyToMatrix(Matrix2D other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }

    public double[][] multiplyToVector(Vector2D vector) {
        double[] homoVector = { vector.getX(), vector.getY(), 1 };
        double[][] result = new double[3][1];

        for (int i = 0; i < 3; i++) {
            result[i][0] = 0;
            for (int j = 0; j < 3; j++) {
                result[i][0] += this.matrix[i][j] * homoVector[j];
            }
        }
        return result;
    }

    // Determinent

    public double determinent() {
        double[][] m = this.matrix;
        return m[0][0] * ((m[1][1] * m[2][2]) - (m[1][2] * m[2][1]))
                - m[0][1] * ((m[1][0] * m[2][2]) - (m[1][2] * m[2][0]))
                + m[0][2] * ((m[1][0] * m[2][1]) - (m[1][1] * m[2][0]));
    }

    // Inverse

    public double[][] inverse() {
        double[][] inversed = new double[3][3];

        double a = this.matrix[0][0];
        double b = this.matrix[0][1];
        double tx = this.matrix[0][2];
        double c = this.matrix[1][0];
        double d = this.matrix[1][1];
        double ty = this.matrix[1][2];

        double determinent = a * d - b * c;

        if (determinent == 0) {
            System.out.println("Warning: Matrix is not invertible, determinent is zero");
            return null;
        }

        double invDet = 1.0 / determinent;

        // 2x2 top-left elements
        inversed[0][0] = d * invDet;
        inversed[0][1] = -b * invDet;
        inversed[1][0] = -c * invDet;
        inversed[1][1] = a * invDet;

        // Translation elements
        inversed[0][2] = (b * ty - d * tx) * invDet;
        inversed[1][2] = (c * tx - a * ty) * invDet;

        // Last row (elements to maintain homogenous)
        inversed[2][0] = 0;
        inversed[2][1] = 0;
        inversed[2][2] = 1;

        return inversed;
    }

    // Transpose
    public double[][] transpose() {
        double[][] transposed = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                transposed[j][i] = this.matrix[i][j];
            }
        }

        return transposed;
    }

    // Scale Matrix

    public double[][] scale(double scaler) {
        double[][] scaled = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                scaled[i][j] = scaler * this.matrix[i][j];
            }
        }

        return scaled;
    }

    // Utilities Methods

    public boolean isIdentity() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    if (this.matrix[i][j] != 1) {
                        return false;
                    }
                } else {
                    if (this.matrix[i][j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isZero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.matrix[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isEqual(Matrix2D other) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.matrix[i][j] != other.matrix[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public double getTrace() {
        return this.matrix[0][0] + this.matrix[1][1] + this.matrix[2][2];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append("[");
            for (int j = 0; j < 3; j++) {
                sb.append(String.format("%.2f", matrix[i][j]));
                if (j < 2)
                    sb.append(", ");
            }
            sb.append("]");
            if (i < 2)
                sb.append("\n"); // new line between rows
        }
        return sb.toString();
    }

    // Engine Related Utils (As they required through development)
}