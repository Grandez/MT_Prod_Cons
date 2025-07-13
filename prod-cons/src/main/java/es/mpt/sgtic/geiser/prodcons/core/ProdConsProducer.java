package es.mpt.sgtic.geiser.prodcons.core;

import es.mpt.sgtic.geiser.prodcons.beans.ResultThread;
import es.mpt.sgtic.geiser.prodcons.config.ProdConsConfig;
import es.mpt.sgtic.geiser.prodcons.logging.QLogger;

import java.util.concurrent.Callable;

public abstract class ProdConsProducer extends ProdConsBase implements Callable<ResultThread> {

   protected abstract  ResultThread productor();

   public ResultThread call() {
       cfg = ProdConsConfig.getConfig(label);
       QLogger.info("Iniciando hilo " + getThreadName());
       em = emf.createEntityManager();
       return productor();
   }
}
