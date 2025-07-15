package com.sdp.poc.threading.matrix;

public class Operacion {
    M res;
    public void multiplica(M m1, M m2) {
        res = new M()
        int[][] r1 = m1.getRows();
        int[][] c2 = m2.getCols();
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
