package com.sdp.poc.threading.matrix.tools;

import com.sdp.poc.threading.matrix.core.Matrix;
import com.sdp.poc.threading.matrix.core.TYPES;

import java.util.Random;

public class MatrixBuilder {
    int[] data;
    int rows;
    int cols;

    public Matrix build(int rows, int cols, TYPES type) {
        this.rows = rows;
        this.cols = cols;
        data = new int[rows * cols];

        switch (type) {
            case RANDOM:     makeRandom();             break;
            case IDENTITY:   makeIdentity();           break;
            case DIAGONAL:   makeDiagonal();           break;
            case DIAG_SUP:   makeDiagonalSuperior();   break;
            case DIAG_INF:   makeDiagonalInferior();   break;
            case TRIANG_SUP: makeTriangularSuperior(); break;
            case TRIANG_INF: makeTriangularInferior(); break;
            default: makeNormal(); break;
        }
        return new Matrix(rows, cols, data);
    }
    private void makeIdentity() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[(row * cols) + col] = (row == col) ? 1 : 0;
            }
        }
    }
    private void makeDiagonal() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[(row * cols) + col] = (row == col) ? row + 1 : 0;
            }
        }
    }
    private void makeTriangularSuperior() {
        int cont = 1;
        int value;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                value = (row - col < 0) ? cont++ : 0;
                data[(row * cols) + col] = value;
            }
        }
    }
    private void makeTriangularInferior() {
        int cont = 1;
        int value;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                value = (row - col > 0) ? cont++ : 0;
                data[(row * cols) + col] = value;
            }
        }
    }
    private void makeDiagonalSuperior() {
        int cont = 1;
        int value;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                value = (row - col < 0) ? cont++ : (row - col == 0) ? row + 1 : 0;
                data[(row * cols) + col] = value;
            }
        }
    }

    private void makeDiagonalInferior() {
        int cont = 1;
        int value;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                value = (row - col > 0) ? cont++ : (row - col == 0) ? row + 1 : 0;
                data[(row * cols) + col] = value;
            }
        }
    }

    private void makeNormal() {
        int cont = 1;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[(row * cols) + col] = cont++ % 1000; // Para evitar overflow
            }
        }
    }
    private void makeRandom() {
        int cont = 1;
        int low  = 2;
        int high = 1000;

        Random r = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[(row * cols) + col] = (r.nextInt(high-low) + low) % 1000; // Para evitar overflow
            }
        }
    }

}
