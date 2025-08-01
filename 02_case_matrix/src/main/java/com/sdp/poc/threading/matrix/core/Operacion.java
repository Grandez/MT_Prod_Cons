package com.sdp.poc.threading.matrix.core;

public class Operacion {
    Matrix res;
    public void multiplica(Matrix matrix1, Matrix matrix2) {
        int[][] r1 = matrix1.getNrows();
        int[][] c2 = matrix2.getNcols();
        res = new Matrix(r1.length, c2.length);
        for (int row = 0; row < r1.length; row++) {
            for (int col = 0; col < c2.length; col++) {
                filaXColumna(row, col, r1[row], c2[col]);
            }
        }
    }
    private void filaXColumna(int row, int col, int[] fila, int[] columna) {
        int res = 0;
        for (int i = 0; i < fila.length; i++) res += (fila[i] * columna[i]);
    }
}
