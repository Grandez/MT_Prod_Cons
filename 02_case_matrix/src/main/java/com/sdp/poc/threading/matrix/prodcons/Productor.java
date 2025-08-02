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
        m = ctx.getMatrix();
        m.print("Source:");
    }
    @Override
    public Long producir() {
        if (++c == m.getNumCols()) {
            r++;
            c = 0;
        }
        if (r == m.getNumRows()) return null;
        String sid = String.format("%04d%04d", r, c);
        return Long.parseLong(sid);
    }
}
