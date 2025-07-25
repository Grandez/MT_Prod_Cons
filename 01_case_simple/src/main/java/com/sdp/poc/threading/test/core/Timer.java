package com.sdp.poc.threading.test.core;

import com.sdp.poc.threading.base.msg.QLogger;
import com.sdp.poc.threading.config.CAMotor;

/**
 * ESte hilo se encarga de matar al resto transcurrido cierto tiempo maximo de ejecucion
 */
public class Timer extends ThreadBase {
    private Thread thrProd;
    private MTEnv env;

    public Timer (Thread thrProd, MTEnv env) {
        this.thrProd = thrProd;
        this.env = env;
        Thread.currentThread().setName("timer");
    }
    @Override
    public void run() {
        QLogger.info("Hilo de tiempo");
        try {
//         while (!isInterrupted()) {  // El hilo sigue ejecutándose hasta que se interrumpe
            QLogger.info("Hilo de tiempo en espera ");
//         Thread.sleep(((cfg.timer * 60) - 6) * 1000);  // El hilo está durmiendo, y podría ser interrumpido
            Thread.currentThread().sleep(env.getTimeout() * 1000);  // El hilo está durmiendo, y podría ser interrumpido
            QLogger.info("Hilo de tiempo despierta ");
            for (int l = 0; l < env.getNumThreads(); l++) env.getQueue().put(env.ENDI); // Notificar fin hilos
            thrProd.interrupt();
            env.getQLog().put(env.ENDS);
//                }
        } catch (InterruptedException e) {
            QLogger.info("El hilo fue interrumpido mientras estaba dormido.");
            // Si el hilo está interrumpido, podemos hacer algo aquí, como finalizarlo.
           Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
        }
    }
}
