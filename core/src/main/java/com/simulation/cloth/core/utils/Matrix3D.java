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

        // Validating if the Input Matrix 4x4 is valid or not
        if (!isValidMatrix(input)) {
            System.out.println("Input Matrix is invalid, Rerouting the contructor to Indentity/Default contructor");
            for (int i = 0; i < 4; i++) {
                this.matrix[i][i] = 1;
            }
            return;
        }

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

    private boolean isValidMatrix(double[][] input) {
        if (input.length == 4) {
            for (int i = 0; i < 4; i++) {
                if (input[i].length != 4) {
                    return false; // return false if column size is invalid
                }
            }
            return true; // true if everything is alright
        } else {
            return false; // return false if row size is invalid
        }
    }

    // Basic Operations

}
