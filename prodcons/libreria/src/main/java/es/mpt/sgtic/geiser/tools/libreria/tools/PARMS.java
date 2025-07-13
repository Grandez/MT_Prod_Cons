package es.mpt.sgtic.geiser.tools.libreria.tools;

/**
 * Parametros que son propios de la aplicacion y no del framework
 * Por ejemplo, en este PoC:
 *   - La accion a ejecutar porque puede ser 3
 *   - La ruta a los archivos XML
 */

import es.mpt.sgtic.geiser.framework.tools.PARM;

public enum PARMS implements PARM {
     ACTION ("ACTION", true, "Cual de las tres opciones ejecutar: Procesar, Cargar, XML (1-2-3)", "1")
    ,PATH   ("PATH", false, "Path para procesar XML", "c:/nas")
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
