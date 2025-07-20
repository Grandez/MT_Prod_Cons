package com.sdp.poc.threading.threading.matrix.prodcons;

import com.sdp.poc.threading.threading.matrix.components.CommareaMatrix;
import com.sdp.poc.threading.threading.matrix.components.Matriz;
import com.sdp.poc.threading.interfaces.IProducer;

public class Productor implements IProducer {
    @Override
    public void run() {
        CommareaMatrix commareaMatrix = CommareaMatrix.getInstance();
        Matriz matriz = commareaMatrix.getMatrix();
        for (int row = 0; row < matriz.getRows().length; row++) {
            for (int col = 0; col < matriz.getCols().length; col++) {
                commareaMatrix.qdat.put((long) row * 1000 + col);
            }
        }
    }
}
