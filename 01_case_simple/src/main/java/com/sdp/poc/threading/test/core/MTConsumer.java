package com.sdp.poc.threading.test.core;

import com.sdp.poc.threading.base.msg.Logger2;
import com.sdp.poc.threading.test.interfaces.IMTConsumer;

public class MTConsumer<Long> extends ThreadBase implements Runnable {

    IMTConsumer consumer;
    MTEnv env;
    public MTConsumer(MTEnv env, IMTConsumer consumer) {
        super(env.getLatch());
        this.env = env;
        this.consumer = consumer;

    }

    @Override
    public void run() {
        long msg;
        boolean rc = true;
        setThreadName("cons");
        Logger2.info("Iniciando hilo " + getNombre());
        try {
            while (true) {
                msg = (long) env.getQueue().take();
                if (msg <= 0 || msg == java.lang.Long.MAX_VALUE) break;
                consumer.consumir(msg);
            }
        } catch (InterruptedException e) {
            Logger2.info(" Interrumpido");
        }
        Logger2.info(" Finalizado");
        env.getLatch().countDown();
    }
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        String name =  proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1]));
        Thread.currentThread().setName(name);
        System.out.println(getNombre());
    }
}
