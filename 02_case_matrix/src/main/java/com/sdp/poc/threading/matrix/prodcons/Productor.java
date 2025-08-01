package com.sdp.poc.threading.matrix.prodcons;

import com.sdp.poc.threading.matrix.core.CtxMatrix;
import com.sdp.poc.threading.matrix.core.Matrix;
import com.sdp.poc.threading.mtlatch.interfaces.IMTProducer;

public class Productor implements IMTProducer {
    CtxMatrix ctx;
    Matrix    m;
    int       r =  0;
    int       c = -1;

    public Productor() {
        ctx = CtxMatrix.getInstance();
        Matrix m = ctx.getMatrix();
        m.print();
    }
    @Override
    public Long producir() {
        // El productor envia fila+columna %04d%04d
        long id = (r * 10000) + ++c;
        if (c == m.getNumCols()) {
            r++;
            c = -1;
            if (r == m.getNumRows()) return null;
        }
        return id;
    }
}
