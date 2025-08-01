package com.sdp.poc.threading.core;

import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.config.CAMT;
import com.sdp.poc.threading.interfaces.IMTProducer;

import java.util.concurrent.PriorityBlockingQueue;

public class MTProducer extends ThreadBase  implements Runnable {
    IMTProducer producer;
    public MTProducer(IMTProducer producer) {
        super();
        this.producer = producer;
    }

    public void run() {
        Long msg;
        setThreadName("Prod");

        CLogger.info("Iniciando hilo " + getName());
        PriorityBlockingQueue<Long> q = CAMT.getInstance().getQueue();
        msg = (Long) producer.producir();
        while (msg > 0) {
            System.out.println(getNombre() + " - Escribe " + msg);
           q.put(msg);
           msg = (Long) producer.producir();
        }
        CLogger.info(" Finaliza Producer");
    }

}
