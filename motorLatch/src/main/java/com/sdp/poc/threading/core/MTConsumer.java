package com.sdp.poc.threading.core;

import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.config.CAMT;
import com.sdp.poc.threading.interfaces.IMTConsumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

public class MTConsumer extends ThreadBase  implements Runnable {

    IMTConsumer consumer;
    public MTConsumer(CountDownLatch latch, IMTConsumer consumer) {
        super(latch);
        this.consumer = consumer;
    }

    @Override
    public void run() {
        Long msg = 0L;
        boolean rc = true;
        setThreadName("cons");
        CLogger.info("Iniciando hilo " + getNombre());
        PriorityBlockingQueue<Long> q = CAMT.getInstance().getQueue();
        try {
            do {
                System.out.println("En MTConsumer");
                msg = q.take();
                System.out.println(getNombre() + " - Recibe " + msg);
                consumer.consumir(msg);
                rc = checkEnd(msg);
            } while (rc);
        } catch (InterruptedException e) {
            CLogger.info(" Interrumpido");
        }
        CLogger.info(" Finalizado");
        this.latch.countDown();
    }
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        String name =  proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1]));
        Thread.currentThread().setName(name);
        System.out.println(getNombre());
    }

    /*
     * Control si recibe el mensaje de acabar
     */
    protected boolean checkEnd(Long msg) {
        System.out.println("CheckENd: " + msg);
        return (msg > 0 && msg < Long.MAX_VALUE);
    }

}
