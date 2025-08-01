package com.sdp.poc.threading.matrix.core;

import java.util.Arrays;

/**
 * Implementa una matriz
 */
public class Matrix {
    int nrows;
    int ncols;
    int[] data;

    int[][] rows;
    int[][] cols;

    public Matrix(int size) {
        this.nrows = size;
        this.ncols = size;
        data = new int[nrows * ncols];
    }
    public Matrix(int nrows, int ncols) {
        this.nrows = nrows;
        this.ncols = ncols;
        data = new int[nrows * ncols];
    }
    public Matrix(int nrows, int ncols, int[] values) {
        this(nrows, ncols);
        data = Arrays.copyOf(values, values.length);
    }
    public Matrix(int nrows, int ncols, boolean nul) {
        this(nrows, ncols);
        makeNull();
    }

    public int   getNumRows()  { return nrows;   }
    public int   getNumCols()  { return ncols;   }
    public int[] getRow(int r) { return rows[r]; }
    public int[] getCol(int c) { return cols[c]; }

    public void split() {
        rows = getRows();
        cols = getCols();
    }
    public int[][] getRows() {
        int[][] r = new int[nrows][ncols];
        for (int i = 0; i < (nrows * ncols); i++) {
            r[i / ncols][i % ncols] = data[i];
        }
        return r;
    }
    public int[][] getCols() {
        int[][] r = new int[nrows][ncols];
        for (int i = 0; i < (nrows * ncols); i++) {
            r[i % ncols][i / ncols] = data[i];
        }
        return r;
    }
    public void set(int row, int col, int value) {
        data[(row * ncols) + col] = value;
    }
    public void print() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            if (i % ncols == 0) str.append("\n");
            str.append(String.format("%4d", data[i]));
        }
        System.out.println(str);
    }
    private void makeNull() {
        for (int row = 0; row < nrows; row++) {
            for (int col = 0; col < ncols; col++) {
                data[(row * ncols) + col] = 0;
            }
        }
    }

}
