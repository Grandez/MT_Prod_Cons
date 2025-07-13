package es.mpt.sgtic.geiser.tools.timer.threads;

/**
 * Productor
 * Simplemente pone en la cola tantos mensajes como hilos definidos
 */

import es.mpt.sgtic.geiser.framework.core.ConfigFramework;
import es.mpt.sgtic.geiser.framework.core.Logger;
import es.mpt.sgtic.geiser.framework.core.Producer;
import es.mpt.sgtic.geiser.framework.interfaces.IProducer;
import es.mpt.sgtic.geiser.tools.timer.base.Config;

public class TimerProd<T> extends Producer<T> implements IProducer {
    private Config cfg = Config.getInstance();

    protected TimerProd(T type) {
        super(type);
    }

    @Override
    public void run() {
        long signal = ConfigFramework.ENDT;
        long cont = 0;
        Long max = System.currentTimeMillis() + (cfg.minutos * 1000);
        try {
            Long cur = 0L;
            while (cur < max) {
                 cur = System.currentTimeMillis();
                cfg.qdat.put(cont++);
                Thread.sleep(2000);
            }
            Logger.info("Productor acaba");
        } catch (InterruptedException ex) {
            Logger.info("Productor interrumpido");
            signal = ConfigFramework.ENDI;
            for (int i = 0; i < cfg.threads; i++) cfg.qdat.put(ConfigFramework.ENDI);
        } finally {
            for (int i = 0; i < cfg.threads; i++) cfg.qdat.put(signal);
        }

    }
}
