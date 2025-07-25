package com.sdp.poc.threading.core;

import com.sdp.poc.threading.base.msg.Logger2;
import com.sdp.poc.threading.config.CAMotor;
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

        Logger2.info("Iniciando hilo " + getName());
        PriorityBlockingQueue<Long> q = CAMotor.getInstance().getQueue();
        msg = (Long) producer.producir();
        while (msg > 0) {
            System.out.println(getNombre() + " - Escribe " + msg);
           q.put(msg);
           msg = (Long) producer.producir();
        }
        Logger2.info(" Finaliza Producer");
    }

}
