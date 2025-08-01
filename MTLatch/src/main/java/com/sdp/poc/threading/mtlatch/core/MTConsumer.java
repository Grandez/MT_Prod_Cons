package com.sdp.poc.threading.mtlatch.core;

import com.sdp.poc.threading.base.config.CtxBase;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.mtlatch.base.ThreadBase;
import com.sdp.poc.threading.mtlatch.interfaces.IMTConsumer;

public class MTConsumer<Long> extends ThreadBase implements Runnable {

    IMTConsumer consumer;
    CtxBase env;
    public MTConsumer(CtxBase ctx, IMTConsumer consumer) {
        super(ctx.getLatch());
        this.env = ctx;
        this.consumer = consumer;

    }

    @Override
    public void run() {
        long msg;
        boolean rc = true;
        setThreadName("cons");
        CLogger.info("Iniciando hilo " + getNombre());
        try {
            while (true) {
                msg = (long) env.getQueue().take();
                if (msg <= 0 || msg == java.lang.Long.MAX_VALUE) break;
                consumer.consumir(msg);
            }
        } catch (InterruptedException e) {
            CLogger.info(" Interrumpido");
        }
        // CLogger.info(" Finalizado");
        env.getLatch().countDown();
    }
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        String name =  proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1]));
        Thread.currentThread().setName(name);
    }
}
