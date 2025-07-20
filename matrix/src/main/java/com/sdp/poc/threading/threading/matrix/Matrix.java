package com.sdp.poc.threading.threading.matrix;

import com.sdp.poc.threading.base.msg.Msg;
import com.sdp.poc.threading.threading.matrix.components.CommareaMatrix;
import com.sdp.poc.threading.threading.matrix.components.Matriz;
import com.sdp.poc.threading.threading.matrix.components.TYPES;
import com.sdp.poc.threading.threading.matrix.prodcons.Consumer;
import com.sdp.poc.threading.threading.matrix.prodcons.Productor;
import com.sdp.poc.threading.core.Motor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Matrix {
    Integer size = 50;
    Integer threads   = null;
    Integer elapsed   = null;

    Properties clpProps = new Properties();

    public Matrix() {

    }
    public static void main(String[] args) {
        Matrix matrix = new Matrix();
        matrix.run(args);
    }
    private void run(String[] args) {
        parseParms(args);
        List<Matriz> lista = creaMatrices();
        for (Matriz matriz : lista) multiplica(matriz);
    }
    private void multiplica(Matriz matriz) {
        CommareaMatrix commareaMatrix = CommareaMatrix.getInstance();
        commareaMatrix.setMatrix(matriz);
        commareaMatrix.setCustomProps(clpProps);

        Motor motor = new Motor("latch");

        motor.run(commareaMatrix, Productor.class, Consumer.class);
    }
    private List<Matriz> creaMatrices() {
        List<Matriz> lista = new ArrayList<Matriz>();
        for (TYPES type : TYPES.values()) {
            Matriz matriz = new Matriz(5, type);
            lista.add(matriz);
            matriz.print();
        }
        return lista;
    }
    private void parseParms(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) != '-') Msg.error(4, "Parametro invalido: " + args[i]);
            switch (args[i].charAt(1)) {
                case 'h': case 'H': showHelp();
                case 's': case 'S': clpProps.put("size", parseInteger(++i, args));    break;
                case 't': case 'T': clpProps.put("threads", parseInteger(++i, args)); break;
                case 'm': case 'M': clpProps.put("timeout", parseInteger(++i, args)); break;
                case 'p': case 'P': clpProps.put("preffix", parseString(++i, args)); break;
            }
        }
    }
    private int parseInteger(int idx, String[] args) {
        if (idx >= args.length) Msg.error(4, "Falta el valor para el parametro: " + args[idx-1]);
        try {
            return Integer.parseInt(args[idx]);
        } catch (NumberFormatException ex) {
            Msg.error(4, "El valor para el parametro: " + args[idx-1] + ".No es numerico");
        }
        return 0;
    }
    private String parseString(int idx, String[] args) {
        if (idx >= args.length) Msg.error(4, "Falta el valor para el parametro: " + args[idx-1]);
        return args[idx];
    }

    private void showHelp() {
        System.out.println("POC para analisis de procesos multihilo");
        System.out.println("Eleva un conjunto de matrices cuadradas al cuadrado");
        System.out.println("\t1. Matriz Diagonal Identidad");
        System.out.println("\t2. Matriz Diagonal");
        System.out.println("\t3. Matriz Triangular Superior");
        System.out.println("\t4. Matriz Triangular Inferior");
        System.out.println("\t5. Matriz Cuadrada");
        System.out.println();
        System.out.println("Uso: java -jar nombre.jar [-t n][-s n][-m n");
        System.out.println("\t   -t n - Numero de hilos");
        System.out.println("\t   -t s - Tamaño de la matriz (50)");
        System.out.println("\t   -t m - Maximo tiempo de ejecucion en minutos (0 - sin limite)");
        System.exit(0);
    }

}
