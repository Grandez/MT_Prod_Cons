package com.sdp.poc.threading.threading.motor.tools;

/**
 * Procesa los argumentos de la linea de comandos y la configuracion
 * La prioridad es:
 *   - Valores por defecto codificados (comun para cualquier entorno)
 *   - Valores de entorno segun prefijo (especifico de un entorno)
 *   - Fichero de configuracion (--config file) especifico de entorno y ejecucion
 *   - Linea de comando (especifico de la ejecucion)
 */

import com.sdp.poc.threading.threading.motor.core.ConfigFramework;
import com.sdp.poc.threading.threading.motor.exceptions.ProdConException;
import com.sdp.poc.threading.threading.motor.exceptions.ProdConExceptionHelp;

import java.io.*;
import java.util.*;

import static com.sdp.poc.threading.threading.motor.tools.Messages.*;

public abstract class ParmsBase {
    private ConfigFramework cfg = ConfigFramework.getInstance();
    private PARM parms = null;

    private String   prfxEnv    = null;
    private String   fileConfig = null;
    protected String[] args;

    public void processArgs(String[] cmdline) throws ProdConException {
         parseArgs(cmdline);
         processEnvironment();
         processFile();
         processCommandLine();
         if (args.length > 0) throw new ProdConException(BAD_PARM, args[0]);
    }
    /*
     * Chequea si:
     *  - Se ha indicado un prefijo para las variables de entorno
     *  - Se ha especificado un fichero alternativo de configuracion
     */
    private void parseArgs(String[] from) throws ProdConException {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < from.length; i++) {
            if (from[i].compareToIgnoreCase("--env") == 0) {
                if ((i + 1) == from.length) throw new ProdConException(MISSING_VALUE, from[i]);
                prfxEnv = from[++i];
                continue;
            }
            if (from[i].compareToIgnoreCase("--config") == 0) {
                if ((i + 1) == from.length) throw new ProdConException(MISSING_VALUE, from[i]);
                fileConfig = from[++i];
                continue;
            }
            if (from[i].compareToIgnoreCase("--help") == 0) {
                throw new ProdConExceptionHelp();
            }
            list.add(from[i]);
        }
        this.args = new String[list.size()];
        list.toArray(args);
    }
    // Procesa las variables de entorno segun prefijo
    private void processEnvironment() {
        String var;
        if (prfxEnv == null) prfxEnv = cfg.preffix;
        if (!prfxEnv.endsWith("_")) prfxEnv = prfxEnv + "_";
        for (PARMBASE p : PARMBASE.values()) {
            var = System.getenv(prfxEnv + p.parm);
            if (var != null) cfg.setParm(p.parm, var);
        }
    }
    // Procesa los parametros de un fichero de configuracion
    private void processFile() throws ProdConException {
        if (fileConfig == null) return;
        try  {
            InputStream input = new FileInputStream(fileConfig);
            Properties prop = new Properties();
            prop.load(input);
            cfg.setParms(prop);
        } catch (Exception ex) {
            throw new ProdConException(BAD_FILE, fileConfig);
        }
    }
    // Procesa los argumentos de la linea de comandos
    private void processCommandLine() throws ProdConException {
        processCommandLineGeneral();
        processCommandLineParticular();
    }
    // Procesa los argumentos del framework
    private void processCommandLineGeneral() throws ProdConException {
        PARMBASE parm;
        ArrayList<String> list = new ArrayList<>();
        for (int idx = 0; idx < args.length; idx++) {
            String sparm = args[idx].substring(2).toUpperCase();
            if ((idx + 1) == args.length) throw new ProdConException(MISSING_VALUE, args[idx]);
            try {
                parm = PARMBASE.valueOf(sparm);
                switch (parm) {
                    case HOST:    cfg.host    = args[++idx]; break;
                    case DBNAME:  cfg.schema  = args[++idx]; break;
                    case PORT:    cfg.port    = checkNum(args[++idx]); break;
                    case THREADS: cfg.threads = checkNum(args[++idx]); break;
                    case CHUNK:   cfg.chunk   = checkNum(args[++idx]); break;
                    case MODE:    cfg.mode    = checkNum(args[++idx]); break;
                    case TIMER:   cfg.timer   = checkNum(args[++idx]); break;
                    case CHECK:   cfg.check   = checkNum(args[++idx]) + 1; break;
                    case USER:    setUserAndPwd(args[++idx]); break;
                }
            } catch (IllegalArgumentException ex) {
                // No es una opcion de las de base
                list.add(args[idx++]);
                list.add(args[idx]);
            }
        }
        this.args = new String[list.size()];
        list.toArray(args);
    }

    // Procesa los argumentos especificos
    abstract protected void processCommandLineParticular() throws ProdConException;

    private void setUserAndPwd (String data) throws ProdConException {
        String[] toks = data.split(cfg.SEP);
        if (toks.length != 2) throw new ProdConException(BAD_USER);
        cfg.user = toks[0];
        cfg.pwd  = toks[1];
    }
    protected int checkNum (String data) throws ProdConException {
        try {
            return Integer.parseInt(data);
        } catch (Exception ex) {
            throw new ProdConException(BAD_NUMBER, data);
        }
    }

/*
    private int parseParmLong(String[] args, int idx) throws LibException {
        PARM parm;
        int rc = 1;
        String sparm = args[idx].substring(2).toUpperCase();
        if (sparm.compareTo("HELP") == 0) printHelp();

        if (idx + 1 == args.length) throw new LibException(Messages.MISSING_VALUE, args[idx]);
        try {
            parm = PARM.valueOf(sparm);
        } catch (IllegalArgumentException ex) {
            throw new LibException(Messages.BAD_PARM, args[idx]);
        }
        switch (parm) {
            case HOST:    cfg.setHost(args[++idx]); break;
            case USER:    cfg.setUser(args[++idx]); break;
            case SCHEMA:  cfg.setSchema(args[++idx]); break;
            case PORT:    cfg.setPort(checkNum(parm,args[++idx])); break;
            case THREADS: cfg.setThreads(checkNum(parm, args[++idx])); break;
            case CHUNK:   cfg.setChunk(checkNum(parm, args[++idx])); break;
            case MODE:    cfg.setMode(checkNum(parm, args[++idx])); break;
            case ACCION:  cfg.setAction(checkNum(parm, args[++idx])); break;
        }

 */
        /*
        if (parm.compareTo("LOAD") == 0) {
            if (idx + 1 == args.length) throw new LibException(Messages.MISSING_VALUE, args[idx]);
            cfg.setAction(1);
            cfg.setRegistros(args[++idx]);
        }
*/
/*
        return rc;
    }

    private void checkEnvironment() {

    }

    private int checkNum(PARM parm, String value) throws LibException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new LibException(BAD_NUMBER, parm.toString(), value);
        }
    }

    private void printHelp() throws LibExceptionHelp {
        //File jarFile = new File (org.classes.main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        pln("Utilidad para migrar registros de las tablas Li (Libreria");
        pln("Uso: java -jar jar_name.jar [parametros]");
        pln("Los parametros se pueden poner en linea de comandos o en variable de entorno");

        PARM[] parms = PARM.values();
        for (int i = 0; i < parms.length; i++) plnp(parms[i]);
        System.exit(0);
    }
    private void pln (String msg, Object... vars) {
        System.out.println(String.format(msg, vars));
    }
    private void plnp (PARM parm) {
        if (parm.publico) System.out.println("\t--" + String.format("%-10s",parm.parm) + parm.desc + " (" + parm.def + ")");
    }
*/
}
