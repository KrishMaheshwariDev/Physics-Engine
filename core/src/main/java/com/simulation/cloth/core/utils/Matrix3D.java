package com.simulation.cloth.core.utils;

/*
 * Message for Future Krish:-
 *      
 *  there might be lot of flaws in this file, if there is any calculation mistake in the engine pls check the Matrix Files first 
 */

public class Matrix3D {
    private double[][] matrix = new double[4][4];

    Matrix3D() { // Default constructor (Identity Matrix)
        for (int i = 0; i < 4; i++) {
            this.matrix[i][i] = 1;
        }
    }

    Matrix3D(double[][] input) { // Raw Math matrix for input

        // // Validating if the Input Matrix 4x4 is valid or not
        // if (!isValidMatrix(input)) {
        // System.out.println("Input Matrix is invalid, Rerouting the contructor to
        // Indentity/Default contructor");
        // for (int i = 0; i < 4; i++) {
        // this.matrix[i][i] = 1;
        // }
        // return;
        // }

        // if input is validated then create the matrix

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.matrix[i][j] = input[i][j];
            }
        }
    }

    Matrix3D(Matrix3D copy) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.matrix[i][j] = copy.matrix[i][j];
            }
        }
    }

    // private boolean isValidMatrix(double[][] input) {
    // if (input.length == 4) {
    // for (int i = 0; i < 4; i++) {
    // if (input[i].length != 4) {
    // return false; // return false if column size is invalid
    // }
    // }
    // return true; // true if everything is alright
    // } else {
    // return false; // return false if row size is invalid
    // }
    // }

    // Basic Operations

    public Matrix3D add(Matrix3D other) {
        Matrix3D result = new Matrix3D();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix3D subtract(Matrix3D other) {
        Matrix3D result = new Matrix3D();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix3D multiply(Matrix3D other) {
        Matrix3D result = new Matrix3D();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result.matrix[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return result;
    }

    public Matrix3D scale(double input) {
        Matrix3D result = new Matrix3D();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[i][j] = this.matrix[i][j] * input;
            }
        }
        return result;
    }

    public Matrix3D transpose() {
        Matrix3D result = new Matrix3D();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.matrix[i][j] = this.matrix[j][i];
            }
        }
        return result;
    }

    public double determinent() {
        double[][] m = this.matrix;
        Matrix2D mat1 = new Matrix2D(new double[][] {
                { m[1][1], m[1][2], m[1][3] },
                { m[2][1], m[2][2], m[2][3] },
                { m[3][1], m[3][2], m[3][3] },
        });

        Matrix2D mat2 = new Matrix2D(new double[][] {
                { m[1][0], m[1][2], m[1][3] },
                { m[2][0], m[2][2], m[2][3] },
                { m[3][0], m[3][2], m[3][3] },
        });

        Matrix2D mat3 = new Matrix2D(new double[][] {
                { m[1][0], m[1][1], m[1][3] },
                { m[2][0], m[2][1], m[2][3] },
                { m[3][0], m[3][1], m[3][3] },
        });

        Matrix2D mat4 = new Matrix2D(new double[][] {
                { m[1][0], m[1][1], m[1][2] },
                { m[2][0], m[2][1], m[2][2] },
                { m[3][0], m[3][1], m[3][2] },
        });

        return m[0][0] * mat1.determinent() - m[0][1] * mat2.determinent() + m[0][2] * mat3.determinent()
                - m[0][3] * mat4.determinent();
    }

    public Matrix3D inverse() {
        double det = this.determinent();
        if (Math.abs(det) < 1e-9) {
            throw new ArithmeticException("Matrix is singular and cannot be inverted.");
        }

        double[][] inv = new double[4][4];
        double[][] m = this.matrix;

        // Compute the adjugate (cofactor transpose)
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // sub matrix (3x3) excluding row i and column j
                double[][] sub = new double[3][3];
                int r = 0;
                for (int row = 0; row < 4; row++) {
                    if (row == i)
                        continue;
                    int c = 0;
                    for (int col = 0; col < 4; col++) {
                        if (col == j)
                            continue;
                        sub[r][c] = m[row][col];
                        c++;
                    }
                    r++;
                }

                // Cofactor expansion: C[i][j] = (-1)^(i+j) * det(sub matrix)
                Matrix2D minor = new Matrix2D(sub);
                double cofactor = Math.pow(-1, i + j) * minor.determinent();

                // Note: adjugate is transpose of cofactor matrix
                inv[j][i] = cofactor / det;
            }
        }

        return new Matrix3D(inv);
    }

    // Physics Related Utilities (lets leave it on tomorrows krish)
}
