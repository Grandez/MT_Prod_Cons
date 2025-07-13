package es.mpt.sgtic.geiser.tools.libreria.processes; /**
 * Factoria
 * Devuelve objetos de cada clase basados en la interfaz
 * Las interfaces son Ixxxx para evitar colisiones de nombres
 *
 * IProceso  - El proceso a ejecutar cuando el Main tiene varias opciones
 * IProducer - El que genera la informacion
 * IConsumer - El que la procesa
 * LoggerCons    - El sistema de log
 */

import es.mpt.sgtic.geiser.framework.interfaces.*;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.processes.loader.*;

import java.util.concurrent.CountDownLatch;

public class Factory {
    private static Factory instance;
    private Config cfg;

    public static Factory getInstance() {
        if (instance == null) { instance = new Factory(); }
        return instance;
    }
    private Factory() {
        cfg = Config.getInstance();
    }
}
