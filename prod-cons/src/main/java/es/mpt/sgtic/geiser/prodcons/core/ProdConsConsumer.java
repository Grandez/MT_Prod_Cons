package es.mpt.sgtic.geiser.prodcons.core;

/**
 * Clase consumidora
 * Version especifica para ID
 * Las clases concretas deben implementar el metodo consumidor(Long id)
 * Va leyendo mensajes hasta que se acaba o se interrumpe por tiempo
 * Por cada mensaje invoca a consumidor(id) que es especifico de la tarea
 * Gestiona la transaccionalidad
 */

import es.mpt.sgtic.geiser.prodcons.beans.ResultThread;
import es.mpt.sgtic.geiser.prodcons.config.ProdConsConfig;
import es.mpt.sgtic.geiser.prodcons.logging.QLogger;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.Callable;

public abstract class ProdConsConsumer extends ProdConsBase implements Callable<ResultThread> {
    protected abstract  void consumidor(Long id) throws InterruptedException;

    /**
     * Ejecuta el hilo hasta que recibe un mensaje de finalizacion
     * Gestiona la transaccionalidad DEL HILO
     */
    public ResultThread call() throws Exception {
        Long msg;
        setThreadName();
        cfg = ProdConsConfig.getConfig(label);

        int count = 0;
        int chunk = cfg.getChunk() > 0 ? cfg.getChunk() : 1;

        QLogger.info("Iniciando hilo " + getThreadName());
        em = emf.createEntityManager();
        tx = em.getTransaction();

        defTx = new DefaultTransactionDefinition();
        defTx.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);

        try {
            while (true) {
                if (count++ % chunk == 0) txBegin();
                msg = cfg.getQueue().take();
                result.incRead();
                if (msg < 0 || msg == Long.MAX_VALUE) break;
                consumidor(msg);
                if (count % chunk == 0) txEnd();
            }
            if (tx.isActive()) tx.commit();
        } catch (InterruptedException e) {
            QLogger.info(" Interrumpido");
        } catch (Exception e) {
            QLogger.exception(e);
            if (tx.isActive()) tx.rollback();
        }
        QLogger.info(" Finalizado");

        // cfg.latch.countDown();
        return result;
    }
}
