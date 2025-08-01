package com.sdp.poc.threading.simple.prodcons;

import com.sdp.poc.threading.mtlatch.interfaces.IMTConsumer;

public class Consumer implements IMTConsumer {

    public void consumir(long msg) {
        System.out.println(Thread.currentThread().getName() + " - Recibe " + msg);
        try {
            Thread.sleep(100); // Esperar una decima de segundo
        } catch (InterruptedException e) {}
    }
}
