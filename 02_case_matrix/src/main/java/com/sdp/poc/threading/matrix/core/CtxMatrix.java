/**
 * Contexto de la aplicacion
 * Unico para toda la aplicacion
 *
 */
package com.sdp.poc.threading.matrix.core;

import com.sdp.poc.threading.base.config.CtxBase;

public class CtxMatrix extends CtxBase {
    Matrix matrix;
    Matrix result;
    int    rows = 10; // Default value

    private CtxMatrix() {}

    private static class CtxInner          { private static final CtxMatrix INSTANCE = new CtxMatrix(); }
    public  static CtxMatrix getInstance() { return CtxInner.INSTANCE; }

    // Si se cambia la matriz, el resultado ya no es valido
    public CtxMatrix setMatrix    (Matrix matrix)         {
        this.matrix = matrix;
        this.result = new Matrix(matrix.getNumRows(), matrix.getNumCols(), true);
        return this;
    }

    public Matrix getMatrix()       { return matrix;    }
    public int    getRows()         { return rows;      }
    public Matrix getResult()       { return result;    }
    public void   setRows(int rows) { this.rows = rows; }
}
