package com.sdp.poc.threading.simple;

import com.sdp.poc.threading.base.config.CLP;
import com.sdp.poc.threading.base.config.CLP_Parameter;
import com.sdp.poc.threading.base.config.CLP_TYPE;
import com.sdp.poc.threading.base.core.MainBase;
import com.sdp.poc.threading.base.logging.QLoggerProd;
import com.sdp.poc.threading.mtlatch.core.Motor;
import com.sdp.poc.threading.simple.core.CtxSimple;
import com.sdp.poc.threading.simple.prodcons.Consumer;
import com.sdp.poc.threading.simple.prodcons.Productor;

import java.util.*;

import static java.lang.System.out;

public class Main extends MainBase {
    private CtxSimple ca = CtxSimple.getInstance();
    private QLoggerProd logger;

    public static void main(String[] args) {
        Main main = new Main();
        main.run(args);
    }
    private void run(String[] args) {
        try {
            appInit("simple", ca, args);
            Motor motor = new Motor(ca);
            motor.run(Productor.class, Consumer.class);
        } catch (SecurityException se) {
           System.err.println("Control-c pulsado");
        } catch (Exception se) {
            System.err.println(se.getLocalizedMessage());
            ca.setRC(32);
        } finally {
            appEnd();
        }

    }
     protected Properties parseParms(String[] args) {
        Map<String, CLP_Parameter> options = new HashMap<>();

        options.put("n", new CLP_Parameter("n", "items", CLP_TYPE.PINT));
        options.put("t", new CLP_Parameter("t", "threads", CLP_TYPE.PINT));
        options.put("e", new CLP_Parameter("e", "timeout", CLP_TYPE.PINT));

        Properties props = CLP.parseParms(args, options);
        if (props.get("help") != null) showHelp();
        return props;
    }

    protected void showHelp() {
        out.println("POC para analisis de procesos multihilo");
        out.println("Simplemente saca un mensaje por consola");
        out.println();
        out.println("Uso: java -jar 01_case_simple.jar [-t n][-e n][-n n]");
        out.println("\t   -t n - Numero de hilos");
        out.println("\t   -e n - Maximo tiempo elapsed en minutos");
        out.println("\t   -n n - Numero de mensajes");
        System.exit(0);
    }

}
