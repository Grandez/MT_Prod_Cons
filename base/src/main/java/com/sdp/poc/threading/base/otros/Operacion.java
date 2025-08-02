package com.sdp.poc.threading.base.otros;

import java.util.Arrays;

public class Operacion {
    final int SIZE = 5000;
    int[] arr = new int[SIZE];
    int idx;
    int inc;
    int last;

    public int sumar (int a, int b) {
        int[] arr = new int[SIZE];
        boolean asc = System.currentTimeMillis() % 2 == 0;
        idx = asc ? 0 : SIZE - 1;
        inc = asc ? 1 :   -1;

        Arrays.fill(arr, 0);
        arr[idx] = last = a + b;
        idx += inc;
        while (idx >= 0 && idx < SIZE) {
            arr[idx] = last + a + b;
            last = arr[idx];
            idx += inc;
        } ;
        return result(arr);
    }
    public int multiplicar (int a, int b) {
        int[] arr = new int[SIZE];
        boolean asc = System.currentTimeMillis() % 2 == 0;
        Arrays.fill(arr, 0);

        idx = asc ? 0 : SIZE - 1;
        inc = asc ? 1 :   -1;

        arr[idx] = last = a * b;
        idx += inc;
        while (idx >= 0 && idx < SIZE) {
            arr[idx] = a * b + last;
            last = arr[idx];
            idx += inc;
        } ;
        return result(arr);
    }
    private int result(int[] arr) {
        int num = 0;
        int den = (1 + SIZE) * (SIZE / 2);
        for (int i = 0; i < SIZE; i++) num += arr[i];
        return num / den;
    }

}
