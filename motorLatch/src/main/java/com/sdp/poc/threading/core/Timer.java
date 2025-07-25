package com.sdp.poc.threading.core;

import com.sdp.poc.threading.base.msg.QLogger;
import com.sdp.poc.threading.config.CAMotor;
import com.sdp.poc.threading.config.Commarea;

/**
 * ESte hilo se encarga de matar al resto transcurrido cierto tiempo maximo de ejecucion
 */
public class Timer extends ThreadBase {
    private Thread thread;
    private CAMotor ca;

    public Timer (Thread thread, CAMotor ca) {
        this.thread = thread;
        this.ca = ca;
    }
    public Timer (String name, Thread thread, CAMotor ca) {
        this(thread,ca);
        Thread.currentThread().setName(name);
    }
        @Override
        public void run() {
            //Commarea cfg = Commarea.getInstance();
            QLogger.info("Hilo de tiempo");
            try {
//                while (!isInterrupted()) {  // El hilo sigue ejecutándose hasta que se interrumpe
                QLogger.info("Hilo de tiempo en espera ");
//                Thread.sleep(((cfg.timer * 60) - 6) * 1000);  // El hilo está durmiendo, y podría ser interrumpido
                Thread.currentThread().sleep(ca.getTimeout() * 1000);  // El hilo está durmiendo, y podría ser interrumpido
                QLogger.info("Hilo de tiempo despierta ");
                for (long l = 0; l < ca.getNumThreads(); l++) ca.qdat.put(ca.ENDI); // Notificar fin hilos
//                ca.qlog.put(Commarea.ENDS);
                thread.interrupt();
//                }
            } catch (InterruptedException e) {
                QLogger.info("El hilo fue interrumpido mientras estaba dormido.");
                // Si el hilo está interrumpido, podemos hacer algo aquí, como finalizarlo.
                Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
            }
        }
}
