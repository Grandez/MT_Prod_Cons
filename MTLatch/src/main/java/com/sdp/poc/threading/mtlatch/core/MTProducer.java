package com.sdp.poc.threading.mtlatch.core;

import com.sdp.poc.threading.base.config.CtxBase;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.mtlatch.base.ThreadBase;
import com.sdp.poc.threading.mtlatch.interfaces.IMTProducer;

public class MTProducer<T> extends ThreadBase implements Runnable {
    IMTProducer producer;
    CtxBase     env;

    public MTProducer(CtxBase env, IMTProducer producer) {
        super();
        this.producer = producer;
        this.env = env;
    }

    public void run() {
        Long msg;
        setThreadName("Prod");

        CLogger.info("Iniciando hilo " + getName());
        msg = producer.producir();
        while (msg != null) {
           System.out.println(getNombre() + " - Escribe " + msg);
           env.getQueue().put(msg);
           msg = producer.producir();
        }
        // Notificar que acaben
        for (long l = 0; l < env.getNumThreads(); l++) env.getQueue().put(Long.MAX_VALUE);
        env.getLatch().countDown();
        CLogger.info(" Finaliza Producer");
    }

}
