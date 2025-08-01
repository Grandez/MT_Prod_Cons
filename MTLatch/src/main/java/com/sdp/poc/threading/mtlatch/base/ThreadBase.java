package com.sdp.poc.threading.mtlatch.base;

import java.util.concurrent.CountDownLatch;

/**
 * Clase base de los consumidores
 * Se encarga de gestionar la transaccionalidad segun los chunks definidos
 */
public abstract class ThreadBase extends Thread {
    protected CountDownLatch latch;
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        Thread.currentThread().setName(proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1])));
    }
    public ThreadBase() {}
    public ThreadBase(CountDownLatch latch) {
        this.latch = latch;
    }
    public String getNombre() {
        return Thread.currentThread().getName();
    }
}
