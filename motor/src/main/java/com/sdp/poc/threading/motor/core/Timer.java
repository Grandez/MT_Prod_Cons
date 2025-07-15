package com.sdp.poc.threading.motor.core;

/**
 * ESte hilo se encarga de matar al resto transcurrido cierto tiempo maximo de ejecucion
 */
public class Timer extends Thread {
    private Thread thread;
    public Timer (Thread thread) {
        this.thread = thread;
    }
        @Override
        public void run() {
            ConfigFramework cfg = ConfigFramework.getInstance();
            Logger.info("Hilo de tiempo");
            try {
//                while (!isInterrupted()) {  // El hilo sigue ejecutándose hasta que se interrumpe
                Logger.info("Hilo de tiempo en espera ");
//                Thread.sleep(((cfg.timer * 60) - 6) * 1000);  // El hilo está durmiendo, y podría ser interrumpido
                Thread.currentThread().sleep(cfg.timer * 1000);  // El hilo está durmiendo, y podría ser interrumpido
                Logger.info("Hilo de tiempo despierta ");
                for (long l = 0 ; l < cfg.threads; l++) cfg.qdat.put(ConfigFramework.ENDI); // Notificar fin hilos
                cfg.qlog.put(ConfigFramework.ENDS);
                thread.interrupt();
//                }
            } catch (InterruptedException e) {
                Logger.info("El hilo fue interrumpido mientras estaba dormido.");
                // Si el hilo está interrumpido, podemos hacer algo aquí, como finalizarlo.
                Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
            }
        }
}
