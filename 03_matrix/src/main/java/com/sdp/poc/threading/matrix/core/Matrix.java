package com.sdp.poc.threading.matrix.core;

import java.util.Arrays;
import java.util.Collections;

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

    /**
     * Saca por consola la matriz formateada
     */
    public void print() { print(""); }
    public void print(String title) {
        int size = getSize();
        Integer aux;
        StringBuilder str = new StringBuilder();

//        System.out.println(title);
        for (int i = 0; i < data.length; i++) {
            if (i > 0 && i % ncols == 0) str.append("\n");
            // Esto falla, aunque deberia estar soportado
            //str.append(String.format("%*d", size, data[i]));

            // Chapu con join y ncopies
            aux = data[i];
            int n = size - aux.toString().length() + 1;
            String pad = String.join("", Collections.nCopies(n, " "));
            str.append(String.format("%s%d", pad, data[i]));
        }
//        System.out.println(str + "\n");
    }
    private void makeNull() {
        for (int row = 0; row < nrows; row++) {
            for (int col = 0; col < ncols; col++) {
                data[(row * ncols) + col] = 0;
            }
        }
    }
    private int getSize() {
        Integer max = 0;
        for (int i = 0; i < data.length; i++) if (data[i] > max) max = data[i];
        return max.toString().length();
    }
}
