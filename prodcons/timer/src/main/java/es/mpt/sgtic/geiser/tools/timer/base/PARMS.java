package es.mpt.sgtic.geiser.tools.timer.base;

/**
 * Parametros que son propios de la aplicacion y no del framework
 * Por ejemplo, en este PoC solo necesitamos el tiempo que se va a estar
 * ejecutando los procesos:
 */

import es.mpt.sgtic.geiser.framework.tools.PARM;

public enum PARMS implements PARM {
     TIEMPO ("TIEMPO", true, "Tiempo en minutos que se ejecutara el proceso ", "5")
    ;

    public final   String parm;
    public final   boolean publico;
    public final   String desc;
    public final   String def;


    PARMS(String parm, boolean publico, String desc, String def ) {
        this.parm = parm;
        this.desc = desc;
        this.def = def;
        this.publico = publico;
    }

}
