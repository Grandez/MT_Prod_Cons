package com.sdp.poc.threading.base.config;

import com.sdp.poc.threading.base.exceptions.InvalidArgumentException;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.base.mask.RC;

import java.util.Map;
import java.util.Properties;

public class CLP {
    static final String badParm  = "Parametro invalido: %s";
    static final String badValue = "El valor para el parametro ";
    static final String invalid  = "%s '%s' no es valido '%s'";

    public static Props parseParms(String[] args, Map<String, CLP_Parameter> options) {
        Props props = new Props();
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].charAt(0) != '-') throw new InvalidArgumentException(badParm, args[i]);
                String parm = args[i].substring(1);

                // Control de ayuda
                if (parm.compareToIgnoreCase("H") == 0) {
                    props.put("help", 1);
                    return props;
                }

                CLP_Parameter option = options.get(parm);
                if (option == null) throw new InvalidArgumentException(badParm, args[i]);

                // Control de argumento valido
                parseParm(++i, args, option, props);
            }
        } catch (InvalidArgumentException ex) {
            CLogger.error(ex.getLocalizedMessage());
            System.exit(RC.ERROR);
        }
        return props;
    }
    private static void parseParm(int idx, String[] args, CLP_Parameter parm, Properties props) throws InvalidArgumentException {
        if (idx >= args.length) throw new InvalidArgumentException("Falta el valor para el parametro: %s" , args[idx-1]);
        switch (parm.type) {
            case INT:    props.put(parm.name, checkInt(args, idx)); break;
            case PINT:   props.put(parm.name, checkPInt(args, idx)); break;
            case STRING: props.put(parm.name, args[idx]); break;
        }
    }
    private static String checkInt(String[] args, int idx) throws InvalidArgumentException {
        try {
            Integer n = Integer.parseInt(args[idx]);
            return n.toString();
        } catch (NumberFormatException ex) {
            throw new InvalidArgumentException("%s '%s' no es entero '%s'", badValue, args[idx-1], args[idx]);
        }
    }
    private static String checkPInt(String[] args, int idx) throws InvalidArgumentException{
        try {
            Integer n = Integer.parseInt(args[idx]);
            if (n < 1) throw new InvalidArgumentException(invalid, badValue, args[idx-1], args[idx]);
            return n.toString();
        } catch (NumberFormatException ex) {
            throw new InvalidArgumentException(invalid, badValue, args[idx-1], args[idx]);
        }
    }

}
