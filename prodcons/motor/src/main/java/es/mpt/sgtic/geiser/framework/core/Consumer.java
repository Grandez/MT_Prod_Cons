package es.mpt.sgtic.geiser.framework.core;

import java.util.concurrent.CountDownLatch;

public abstract class Consumer<T> extends ProdCons<T>  implements Runnable {
    protected CountDownLatch latch;

    public Consumer (CountDownLatch latch, T dummy) {
        super(dummy);
        this.latch = latch;
    }

    public abstract  void consumidor(T obj) throws InterruptedException;

    @Override
    public void run() {
        T msg;
        setThreadName();
        Logger.info("Iniciando hilo " + getName());

        try {
            do {
                msg = (T) cfg.qdat.take();
                consumidor(msg);
            // } while (id > 0 && id != Long.MAX_VALUE);
            } while (checkEnd(msg));
        } catch (InterruptedException e) {
            Logger.info(" Interrumpido");
        }
        Logger.info(" Finalizado");
        this.latch.countDown();
    }
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        Thread.currentThread().setName(proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1])));
    }
    protected String getName() { return Thread.currentThread().getName(); }
    /*
     * Control si recibe el mensaje de acabar
     */
    protected boolean checkEnd(T msg) {
        msg.compareTo
        Long value = (Long) msg;
        return (value > 0 && value < Long.MAX_VALUE);
    }

}
