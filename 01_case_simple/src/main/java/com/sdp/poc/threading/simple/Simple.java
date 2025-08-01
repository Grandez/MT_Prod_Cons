package com.sdp.poc.threading.simple;

import com.sdp.poc.threading.base.config.CLP;
import com.sdp.poc.threading.base.config.CLP_Parameter;
import com.sdp.poc.threading.base.config.CLP_TYPE;
import com.sdp.poc.threading.base.datetime.Time;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.base.logging.QLogger;
import com.sdp.poc.threading.base.logging.QLoggerProd;
import com.sdp.poc.threading.base.system.Shutdown;
import com.sdp.poc.threading.mtlatch.core.Motor;
import com.sdp.poc.threading.simple.base.CASimple;
import com.sdp.poc.threading.simple.prodcons.Consumer;
import com.sdp.poc.threading.simple.prodcons.Productor;

import java.util.*;

import static java.lang.System.out;

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
        } catch (SecurityException se) {
           System.err.println("Control-c pulsado");
        } catch (Exception se) {

            System.err.println(se.getLocalizedMessage());
            ca.setRC(32);
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
        CLogger.info(String.format("RC: %2d - Elapsed: %s", ca.getRC(),
                                   Time.elapsed(System.currentTimeMillis() - ca.getBeg())));
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
        out.println("POC para analisis de procesos multihilo");
        out.println("Simplemente saca un mensaje por consola");
        out.println();
        out.println("Uso: java -jar 01_case_simple.jar [-t n][-n n]");
        out.println("\t   -t n - Numero de hilos");
        out.println("\t   -n n - Numero de mensajes");
        System.exit(0);
    }

}
