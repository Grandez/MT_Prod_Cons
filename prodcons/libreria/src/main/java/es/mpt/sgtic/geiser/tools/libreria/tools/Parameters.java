package es.mpt.sgtic.geiser.tools.libreria.tools;
/**
 * Los parametros se validan en dos fases:
 * Primero los que son propios del framework: hilos, chunks, usuario, etc.
 * Luego los que son propios de la aplicacion
 *
 * De igual forma sacaremos la ayuda
 *
 */

import es.mpt.sgtic.geiser.framework.exceptions.ProdConException;
import es.mpt.sgtic.geiser.framework.tools.ParmsBase;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;

import java.util.ArrayList;

import static es.mpt.sgtic.geiser.framework.tools.Messages.BAD_PARM;
import static es.mpt.sgtic.geiser.framework.tools.Messages.MISSING_VALUE;

public class Parameters extends ParmsBase {
    PARMS[] parms;
    public Parameters(PARMS[] parms) {
        super();
        this.parms = parms;
    }
    protected void processCommandLineParticular() throws ProdConException {
        PARMS parm;
        Config cfg = Config.getInstance();
        ArrayList<String> list = new ArrayList<>();
        for (int idx = 0; idx < args.length; idx++) {
            String sparm = args[idx].substring(2).toUpperCase();
            if ((idx + 1) == args.length) throw new ProdConException(MISSING_VALUE, args[idx]);
            try {
                parm = PARMS.valueOf(sparm);
                switch (parm) {
                    case ACTION:  cfg.action  = checkNum(args[++idx]); break;
                    case PATH:    cfg.path    = args[++idx]; break;
                }
            } catch (IllegalArgumentException ex) {
                throw new ProdConException(BAD_PARM, args[idx]);
            }
        }
        this.args = new String[list.size()];
        list.toArray(args);
    }
}
