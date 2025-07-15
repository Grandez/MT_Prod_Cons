package com.sdp.poc.threading.matrix;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
    int size = 50;
    public Matrix() {

    }
    public Matrix (int size) {
        this.size = size;
    }
    public static void main(String[] args) {
        int size = 50;
        if (args.length > 0) {
            if (args[0].compareToIgnoreCase("-h") == 0 ||
                args[0].compareToIgnoreCase("-help") == 0) {
                showHelp();
            }
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("El parametro debe ser un numero");
                System.exit(127);
            }
        }
        Matrix matrix = new Matrix(size);
        matrix.run();

    }
    private static void showHelp() {
        System.out.println("POC para analisis de procesos multihilo");
        System.out.println("Eleva un conjunto de matrices cuadradas al cuadrado");
        System.out.println("\t1. Matriz Diagonal Identidad");
        System.out.println("\t2. Matriz Diagonal");
        System.out.println("\t3. Matriz Triangular Superior");
        System.out.println("\t4. Matriz Triangular Inferior");
        System.out.println("\t5. Matriz Cuadrada");
        System.out.println("Uso: java -jar nombre.jar [50]");
        System.out.println("\t   50 (Valor por defecto) es el tamaño de la matriz");
        System.exit(0);
    }
    private void run() {
        List<M> lista = new ArrayList<M>();
        for (TYPES type : TYPES.values()) {
            M m = new M(5, type);
            lista.add(m);
            m.print();
        }
    }
}
