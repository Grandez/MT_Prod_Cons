package com.sdp.poc.threading.simple.prodcons;

import com.sdp.poc.threading.simple.base.CA;
import com.sdp.poc.threading.interfaces.IProducer;

public class Productor implements IProducer {
    @Override
    public void run() {
        CA ca = CA.getInstance();
        for (int count = 0; count < ca.getItems(); count++) {
            ca.qdat.put((long) count);
        }
    }
}
