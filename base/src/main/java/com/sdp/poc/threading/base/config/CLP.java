package com.sdp.poc.threading.base.config;

import com.sdp.poc.threading.base.msg.LoggerCon;

import java.util.Map;
import java.util.Properties;

public class CLP {
    public static Properties parseParms(String[] args, Map<String, CLP_Parameter> options) {
        Properties props = new Properties();
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) != '-') LoggerCon.error(4, "Parametro invalido: " + args[i]);
            String parm = args[i].substring(1);

            // Control de ayuda
            if (parm.compareToIgnoreCase("H") == 0) {
                props.put("help", 1);
                return props;
            }

            CLP_Parameter option = options.get(parm);
            if (option == null) LoggerCon.error(4, "Parametro invalido: " + args[i]);

            // Control de argumento valido
            parseParm(++i, args, option, props);
        }
        return props;
    }
    private static void parseParm(int idx, String[] args, CLP_Parameter parm, Properties props) {
        if (idx >= args.length) LoggerCon.error(4, "Falta el valor para el parametro: " + args[idx-1]);
        switch (parm.type) {
            case INT:  props.put(parm.name, checkInt(args, idx)); break;
            case PINT: props.put(parm.name, checkPInt(args, idx)); break;
            case STRING: props.put(parm.name, args[idx]); break;
        }
    }
    private static int checkInt(String[] args, int idx) {
        try {
            return Integer.parseInt(args[idx]);
        } catch (NumberFormatException ex) {
            LoggerCon.error(4, "El valor para el parametro: " + args[idx-1] + ".No es entero");
        }
        return 0;
    }
    private static int checkPInt(String[] args, int idx) {
       int value = checkInt(args, idx);
       if (value < 1) LoggerCon.error(4, "El valor para el parametro: " + args[idx-1] + ".No es valido");
       return value;
    }

}
