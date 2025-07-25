package com.sdp.poc.threading.simple.prodcons;

import com.sdp.poc.threading.test.interfaces.IMTConsumer;

public class Consumer implements IMTConsumer {

    public void consumir(long msg) {
        System.out.println("Recibido " + msg);
    }
}
