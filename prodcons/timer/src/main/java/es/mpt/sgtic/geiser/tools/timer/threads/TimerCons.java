package es.mpt.sgtic.geiser.tools.timer.threads;

import es.mpt.sgtic.geiser.framework.core.Consumer;
import es.mpt.sgtic.geiser.framework.core.Logger;
import es.mpt.sgtic.geiser.framework.interfaces.IConsumer;
import es.mpt.sgtic.geiser.tools.timer.base.Config;

import java.util.concurrent.CountDownLatch;

public class TimerCons extends Consumer<Long> implements IConsumer {
    private Config cfg;
    private Long dummy;
    public TimerCons(CountDownLatch latch) { super(latch, new Long(0)); }

    @Override
    public void consumidor(Long id) throws InterruptedException {
        Logger.info(" activo %5d", 33); // id );
        Thread.currentThread().sleep(15000);
    }

/*
    @Override
    public void run() {
        long id;
        Long max = System.currentTimeMillis() + (cfg.minutos * 1000);
        setThreadName();
        Logger.info("Iniciando hilo " + getName());

        try {
            do {
                id = cola.take();
            } while (id > 0 && id != Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Logger.info(" Interrumpido");
        }
        Logger.info(" Finalizado");
        this.latch.countDown();
    }

 */
}
