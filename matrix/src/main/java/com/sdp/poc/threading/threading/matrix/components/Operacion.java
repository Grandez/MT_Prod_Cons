package com.sdp.poc.threading.threading.matrix.components;

public class Operacion {
    Matriz res;
    public void multiplica(Matriz matriz1, Matriz matriz2) {
        int[][] r1 = matriz1.getRows();
        int[][] c2 = matriz2.getCols();
        res = new Matriz(r1.length, c2.length);
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
