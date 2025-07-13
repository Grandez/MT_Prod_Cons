package es.mpt.sgtic.geiser.prodcons.core;

import es.mpt.sgtic.geiser.prodcons.config.ProdConsConfig;
import es.mpt.sgtic.geiser.prodcons.interfaces.IProdConsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ESte hilo se encarga de matar al resto transcurrido cierto tiempo maximo de ejecucion
 */
public class ProdConsTimer extends Thread {
    private Thread  thread;
    private IProdConsConfig cfg;
    private Logger log = LoggerFactory.getLogger(getClass());

    public ProdConsTimer(Thread thread, IProdConsConfig cfg) {
        this.thread = thread;
        this.cfg    = cfg;
    }
    @Override
    public void run() {
       log.info("Hilo de tiempo");
       try {
//                while (!isInterrupted()) {  // El hilo sigue ejecutándose hasta que se interrumpe
          log.info("Hilo de tiempo en espera ");
//                Thread.sleep(((cfg.timer * 60) - 6) * 1000);  // El hilo está durmiendo, y podría ser interrumpido
          Thread.currentThread().sleep(cfg.getTimer() * 1000);  // El hilo está durmiendo, y podría ser interrumpido
          log.info("Hilo de tiempo despierta ");
                for (long l = 0 ; l < cfg.getThreads(); l++) cfg.getQueue().put(ProdConsConfig.END_INT); // Notificar fin hilos
                cfg.getQLog().put(ProdConsConfig.ENDS);
                thread.interrupt();
//                }
            } catch (InterruptedException e) {
                log.info("El hilo fue interrumpido mientras estaba dormido.");
                // Si el hilo está interrumpido, podemos hacer algo aquí, como finalizarlo.
                Thread.currentThread().interrupt();  // Restablecer el estado de interrupción
            }
        }
}
