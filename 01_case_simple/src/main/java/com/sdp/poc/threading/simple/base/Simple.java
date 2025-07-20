package com.sdp.poc.threading.simple.base;

import com.sdp.poc.threading.base.config.CLP;
import com.sdp.poc.threading.base.config.CLP_Parameter;
import com.sdp.poc.threading.base.config.CLP_TYPE;
import com.sdp.poc.threading.simple.prodcons.Consumer;
import com.sdp.poc.threading.simple.prodcons.Productor;
import com.sdp.poc.threading.core.Motor;

import java.util.*;

public class Simple {
    public Simple() {
    }
    public static void main(String[] args) {
        Simple simple = new Simple();
        simple.run(args);
    }
    private void run(String[] args) {
        Properties props = parseParms(args);
        CA ca = CA.getInstance();
        ca.setItems((String) props.get("items"));
        ca.setCustomProps(props);

        Motor motor = new Motor("latch");

        motor.run(ca, Productor.class, Consumer.class);
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
