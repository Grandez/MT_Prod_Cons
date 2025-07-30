package com.sdp.poc.threading.simple.base;

import com.sdp.poc.threading.base.config.CLP;
import com.sdp.poc.threading.base.config.CLP_Parameter;
import com.sdp.poc.threading.base.config.CLP_TYPE;
import com.sdp.poc.threading.base.logging.QLogger;
import com.sdp.poc.threading.base.logging.QLoggerProd;
import com.sdp.poc.threading.base.system.Shutdown;
import com.sdp.poc.threading.simple.prodcons.Consumer;
import com.sdp.poc.threading.simple.prodcons.Productor;
import com.sdp.poc.threading.test.core.Motor;

import java.util.*;

public class Simple {
    private CASimple ca;
    private QLoggerProd logger;

    public static void main(String[] args) {
        Simple simple = new Simple();
        simple.run(args);
    }
    private void run(String[] args) {
        try {
            appInit(args);

            Motor motor = new Motor(ca);
            motor.run(Productor.class, Consumer.class);
            appEnd();

        } catch (SecurityException se) {
           System.err.println("Control-c pulsado");
        } finally {
            appEnd();
        }

    }
    private void appInit(String[] args) {
        Shutdown.setHook();
        QLogger.start("simple");
        ca = CASimple.getInstance("simple");
        ca.setProperties(parseParms(args));
        ca.setAppName("simple");
        logger = QLogger.getLogger(ca);
    }
    private void appEnd() {
        logger.msg("SMR01000", System.currentTimeMillis() - ca.getBeg()
                                  , ca.getRC()
                                  , ca.getInput(), ca.getOutput(), ca.getErrors()
                                  , ca.getNumThreads(),ca.getChunk(),ca.getTimeout()
        );
        QLogger.stop();
        System.exit(ca.getRC());
    }

    private Properties parseParms(String[] args) {
        Map<String, CLP_Parameter> options = new HashMap<>();

        options.put("n", new CLP_Parameter("n", "items", CLP_TYPE.INT));
        options.put("t", new CLP_Parameter("t", "threads", CLP_TYPE.INT));

        Properties props = CLP.parseParms(args, options);
        if (props.get("help") != null) showHelp();
        return props;
    }
    private void showHelp() {
        System.out.println("POC para analisis de procesos multihilo");
        System.out.println("Simplemente saca un mensaje por consola");
        System.out.println();
        System.out.println("Uso: java -jar 01_case_simple.jar [-t n][-n n]");
        System.out.println("\t   -t n - Numero de hilos");
        System.out.println("\t   -n n - Numero de mensajes");
        System.exit(0);
    }

}
