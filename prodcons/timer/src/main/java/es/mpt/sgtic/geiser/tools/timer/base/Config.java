package es.mpt.sgtic.geiser.tools.timer.base;
/**
 * Caso de uso de fichero de configuracion Dummy
 */

import es.mpt.sgtic.geiser.framework.core.ConfigFramework;

public class Config extends ConfigFramework {
    private static Config instance;
    public int minutos = 5;

    private Config() {
        super(0);
    }

    public static Config getInstance() {
        if (instance == null) instance = new Config();
        return instance;
    }
}