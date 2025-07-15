package com.sdp.poc.threading.matrix;

/**
 * Implementa una matriz
 */
public class M {
    int rows;
    int cols;
    int[] data;
    public M(int size) {
        this.rows = size;
        this.cols = size;
        data = new int[rows * cols];
    }
    public M(int size, TYPES type) {
        this(size);
        build(type);
    }
    public int[][] getRows() {
        int[][] r = new int[rows][cols];
        for (int i = 0; i < (rows * cols); i++) {
            r[i / cols][i % cols] = data[i];
        }
        return r;
    }
    public int[][] getCols() {
        int[][] r = new int[rows][cols];
        for (int i = 0; i < (rows * cols); i++) {
            r[i % cols][i / cols] = data[i];
        }
        return r;
    }
    public void set(int row, int col, int value) {
        data[(row * cols) + col] = value;
    }
    public void print() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            if (i % cols == 0) str.append("\n");
            str.append(String.format("%4d", data[i]));
        }
        System.out.println(str);
    }

    private void build(TYPES type) {
        switch (type) {
            case IDENTITY:   makeIdentity();           break;
            case DIAGONAL:   makeDiagonal();           break;
            case DIAG_SUP:   makeDiagonalSuperior();   break;
            case DIAG_INF:   makeDiagonalInferior();   break;
            case TRIANG_SUP: makeTriangularSuperior(); break;
            case TRIANG_INF: makeTriangularInferior(); break;
            default: makeNormal(); break;
        }
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
                data[(row * cols) + col] = cont++;
            }
        }
    }

}
