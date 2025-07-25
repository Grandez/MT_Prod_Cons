package com.sdp.poc.threading.simple.prodcons;

import com.sdp.poc.threading.simple.base.CA;
import com.sdp.poc.threading.interfaces.IMTProducer;

public class Productor implements IMTProducer {
    CA ca;
    int last = 0;
    int max = 5;
    public Productor() {
        ca = CA.getInstance();
        // max = ca.getItems();
    }
    @Override
    public Long producir() {
        last++;
        if (last > max) return null;
        return last + 0L;
    }
}
