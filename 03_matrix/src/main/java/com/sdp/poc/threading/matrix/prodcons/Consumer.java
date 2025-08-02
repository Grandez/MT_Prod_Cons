package com.sdp.poc.threading.matrix.prodcons;

import com.sdp.poc.threading.base.otros.Operacion;
import com.sdp.poc.threading.matrix.core.CtxMatrix;
import com.sdp.poc.threading.matrix.core.Matrix;
import com.sdp.poc.threading.mtlatch.interfaces.IMTConsumer;

public class Consumer implements IMTConsumer {
    CtxMatrix ctx;
    Matrix    m;
    Operacion op = new Operacion();

    public Consumer() {
        ctx = CtxMatrix.getInstance();
        m = ctx.getMatrix();
    }

    public void consumir(long msg) {
//        System.out.println(Thread.currentThread().getName() + " - Recibe " + msg);

        int row = (int) msg / 10000;
        int col = (int) msg % 10000;
        int val = 0;
        int[] r = m.getRow(row);
        int[] c = m.getCol(col);

        // Ambas dimensiones son iguales (Si no chequear errores)
        for (int i = 0; i < r.length; i++) {
            val = op.sumar(val, op.multiplicar(r[i] ,c[i]));
        }
        ctx.getResult().set(row, col, val);
    }
}
