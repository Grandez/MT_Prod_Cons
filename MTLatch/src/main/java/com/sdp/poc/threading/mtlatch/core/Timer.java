package com.sdp.poc.threading.mtlatch.core;

import com.sdp.poc.threading.base.CtxBase;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.mtlatch.base.ThreadBase;

/**
 * ESte hilo se encarga de matar al resto transcurrido cierto tiempo maximo de ejecucion
 */
public class Timer extends ThreadBase {
    private Thread thrProd;
    private CtxBase env;

    public Timer (Thread thrProd, CtxBase ctx) {
        this.thrProd = thrProd;
        this.env     = ctx;
        Thread.currentThread().setName("timer");
    }
    @Override
    public void run() {
        CLogger.info("Hilo de tiempo");
        try {
//         while (!isInterrupted()) {  // El hilo sigue ejecutándose hasta que se interrumpe
            CLogger.info("Hilo de tiempo en espera ");
//         Thread.sleep(((cfg.timer * 60) - 6) * 1000);  // El hilo está durmiendo, y podría ser interrumpido
            Thread.currentThread().sleep(env.getTimeout() * 1000);  // El hilo está durmiendo, y podría ser interrumpido
            CLogger.info("Hilo de tiempo despierta ");
            for (int l = 0; l < env.getNumThreads(); l++) env.getQueue().put(-1l); // Notificar fin hilos
            thrProd.interrupt();
//                }
        } catch (InterruptedException e) {
            CLogger.info("El hilo fue interrumpido mientras estaba dormido.");
            // Si el hilo está interrumpido, podemos hacer algo aquí, como finalizarlo.
           Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
        }
        CLogger.info("Sale del timer");
    }
}
