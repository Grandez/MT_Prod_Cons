package es.mpt.sgtic.geiser.tools.libreria.processes.XML;

import es.mpt.sgtic.geiser.framework.core.LoggerCons;
import es.mpt.sgtic.geiser.framework.interfaces.IProducer;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;

public class XMLProd implements IProducer {
    private Config cfg = Config.getInstance();
    @Override
    public void run() {
//        LoggerCons.info("Iniciando carga para %dM asientos", cfg.registros);
//        LoggerCons.info("Procesando bloques de %d ", cfg.chunk);
    }
}
