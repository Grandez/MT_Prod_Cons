package com.sdp.poc.threading.mtlatch.core;

import com.sdp.poc.threading.base.CtxBase;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.mtlatch.base.ThreadBase;
import com.sdp.poc.threading.mtlatch.interfaces.IMTProducer;

public class MTProducer<T> extends ThreadBase implements Runnable {
    IMTProducer producer;
    CtxBase ctx;

    public MTProducer(CtxBase ctx, IMTProducer producer) {
        super();
        this.producer = producer;
        this.ctx = ctx;
    }

    public void run() {
        Long msg;
        setThreadName("Prod");

        CLogger.info("Iniciando hilo " + getName());
        msg = producer.producir();
        while (msg != null) {
//           System.out.println(getNombre() + " - Escribe " + msg);
           ctx.read();
           ctx.getQueue().put(msg);
           msg = producer.producir();
        }
        // Notificar que acaben
        for (long l = 0; l < ctx.getNumThreads(); l++) ctx.getQueue().put(Long.MAX_VALUE);
        ctx.getLatch().countDown();
    }

}
