package com.sdp.poc.threading.test.core;
/**
 * Motor del paradigma Porductor/Consumidor
 * Recibe las dos clases asociadas:
 *  - El productor
 *  - El Consumidor
 *
 *  Se encarga de ejecutar el sistema de acuerdo con
 *  la parametrizacion de hilos
 *
 *  Primero se inicia Logger
 *  Luego Consumidores
 *  Luego Productor
 *  Se finaliza en orden inverso
 *
 */

import com.sdp.poc.threading.base.config.Props;
import com.sdp.poc.threading.base.logging.CLogger;
import com.sdp.poc.threading.test.base.CAMT;
import com.sdp.poc.threading.test.interfaces.IMTCA;
import com.sdp.poc.threading.test.interfaces.IMTConsumer;
import com.sdp.poc.threading.test.interfaces.IMTProducer;
import com.sdp.poc.threading.base.logging.QLoggerProd;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Motor {
    ExecutorService executor = null;
    IMTCA env;
    long    beg;
    QLoggerProd logger;

    public Motor()                          { this(null, null, null); }
    public Motor(String file)               { this(file, null, null);    }
    public Motor(String file, String label) {
        this(file, label, null);
    }
    public Motor(IMTCA ca)                  { this(null,null, ca);    }

    /**
     * Constructor del motor de threading
     * @param fConfig Fichero de configuracion, si null mt.properties
     * @param label   Prefijo de las entradas de configuracion
     * @param ca      Parametros pasados por linea de comandos en la interfaz
     */
    public Motor(String fConfig, String label, IMTCA ca) {
        String fname = "mt.properties";
        if (fConfig != null) {
            fname = fConfig;
            if (fConfig.indexOf('.') == -1) fname = fname + ".properties";
        }
        env = ca;
        loadPropsData(Props.load(fname), label);
        loadPropsData(ca.getCustomProps(), null);
    }

    public void run(Class prodClass, Class consClass) {
        beg = System.currentTimeMillis();
        Thread thTimer;
        // Sumamos el productor
        executor = Executors.newFixedThreadPool(env.getNumThreads() + 1);

        try {
            // Arrancamos los consumidores
            for (int i = 0; i < env.getNumThreads(); i++) {
                IMTConsumer iCons = (IMTConsumer) consClass.getConstructor().newInstance();
                MTConsumer consumer = new MTConsumer(env, iCons);
                executor.execute(consumer); // Consumers
            }

            IMTProducer iprod = (IMTProducer) prodClass.getConstructor().newInstance();
            Thread thrProd = new Thread(new MTProducer(env, iprod));

            thTimer = startTimer(thrProd);

            executor.execute(thrProd);
            executor.shutdown();          // No mas hilos
            env.getLatch().await();

            // Si habia timer, ha acabado antes de tiempo. Pararlo
            if (env.getTimeout() > 0) {
                thTimer.interrupt();
                thTimer.join();
            }
            summary();
        } catch (InterruptedException ex) {
            System.err.println(ex.getLocalizedMessage());
        } catch(Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
            System.out.println(ex);
        }
    }
    // Arranca el monitor de tiempo
    public Thread startTimer(Thread prod) {
        if (env.getTimeout() == 0) return null;
        Thread thr = new Thread(new Timer(prod, env));
        thr.start();
        return thr;
    }
    private void loadPropsData(Properties props, String prfx) {
        if (props == null) return;
        String value = "";
        try {
            value = props.getProperty(prfx == null ? "threads" : prfx + ".threads");
            if (value != null) env.setNumThreads(Integer.parseInt(value));
            value = props.getProperty(prfx == null ? "timeout" : prfx + ".timeout");
            if (value != null) env.setTimeout(Integer.parseInt(value));
//            value = props.getProperty("appName");
//            if (value != null) env.setAppName(value);
        } catch (NumberFormatException ex) {
            CLogger.warning("Ignorado valor del atributo no numerico: " + value);
        }
    }
    private void summary() {
//        logger.msg(MSG.SUMMARY, new Long(System.currentTimeMillis() - beg)
//                              ,env.getNumThreads()
//                              ,env.getChunk()
//                              ,env.getRead());
    }
}
