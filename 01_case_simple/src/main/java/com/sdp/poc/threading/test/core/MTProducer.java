package com.sdp.poc.threading.test.core;

import com.sdp.poc.threading.base.msg.Logger2;
import com.sdp.poc.threading.interfaces.IMTProducer;

public class MTProducer<T> extends ThreadBase implements Runnable {
    IMTProducer producer;
    MTEnv env;
    public MTProducer(MTEnv env, IMTProducer producer) {
        super();
        this.producer = producer;
        this.env = env;
    }

    public void run() {
        Long msg;
        setThreadName("Prod");

        Logger2.info("Iniciando hilo " + getName());
        msg = producer.producir();
        while (msg != null) {
            System.out.println(getNombre() + " - Escribe " + msg);
           env.getQueue().put(msg);
           msg = producer.producir();
        }
        // Notificar que acaben
        for (long l = 0; l < env.getNumThreads(); l++) env.getQueue().put(env.ENDT);
        env.getLatch().countDown();
        Logger2.info(" Finaliza Producer");
    }

}
