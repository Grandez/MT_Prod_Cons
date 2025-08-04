package com.sdp.poc.threading.mtlatch.core;

import com.sdp.poc.threading.base.CtxBase;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.mtlatch.base.ThreadBase;
import com.sdp.poc.threading.mtlatch.interfaces.IMTConsumer;

public class MTConsumer<Long> extends ThreadBase implements Runnable {

    IMTConsumer consumer;
    CtxBase ctx;
    public MTConsumer(CtxBase ctx, IMTConsumer consumer) {
        super(ctx.getLatch());
        this.ctx = ctx;
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
                msg = (long) ctx.getQueue().take();
                if (msg < 0 || msg == java.lang.Long.MAX_VALUE) break;
                ctx.write();
                consumer.consumir(msg);
            }
        } catch (InterruptedException e) {
            CLogger.info(" Interrumpido");
        }
        ctx.getLatch().countDown();
    }
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        String name =  proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1]));
        Thread.currentThread().setName(name);
    }
}
