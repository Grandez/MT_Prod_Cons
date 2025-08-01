package com.sdp.poc.threading.simple.prodcons;

import com.sdp.poc.threading.simple.core.CtxSimple;
import com.sdp.poc.threading.mtlatch.interfaces.IMTProducer;

public class Productor implements IMTProducer {
    int last = 0;
    int max = 1000;
    CtxSimple ca;
    public Productor() {
        ca = CtxSimple.getInstance();
        max = ca.getItems();
    }
    @Override
    public Long producir() {
        last++;
        if (last > max) return null;
        ca.read();
        return last + 0L;
    }
}
