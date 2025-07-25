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
import com.sdp.poc.threading.base.msg.Logger2;
import com.sdp.poc.threading.base.msg.QLogger;
import com.sdp.poc.threading.config.CAMotor;
import com.sdp.poc.threading.interfaces.IMTProducer;
import com.sdp.poc.threading.test.base.MSG;
import com.sdp.poc.threading.test.interfaces.IMTConsumer;
import com.sdp.poc.threading.test.interfaces.IMTLogger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Motor {
    ExecutorService executor = null;
    MTEnv   env;
    long    beg;
    MTLoggerProd logger;

    public Motor()                          { this(null, null, null); }
    public Motor(String file)               { this(file, null, null);    }
    public Motor(String file, String label) {
        this(file, label, null);
    }
    public Motor(Properties custom)         { this(null,null,custom);    }

    /**
     * Constructor del motor de threading
     * @param fConfig Fichero de configuracion, si null mt.properties
     * @param label   Prefijo de las entradas de configuracion
     * @param custom  Parametros pasados por linea de comandos
     */
    public Motor(String fConfig, String label, Properties custom) {
        String fname = "mt.properties";
        if (fConfig != null) {
            fname = fConfig;
            if (fConfig.indexOf('.') == -1) fname = fname + ".properties";
        }
        env = new MTEnv();
        loadPropsData(Props.load(fname), label);
        loadPropsData(custom, null);
        logger = new MTLoggerProd(env);
    }

    public void run(Class prodClass, Class consClass, Class logClass) {
        beg = System.currentTimeMillis();
        Thread thTimer;
        Thread thLog;

        // Sumamos el productor
        executor = Executors.newFixedThreadPool(env.getNumThreads() + 1);

        try {
            IMTLogger ilogger = (IMTLogger) logClass.getConstructor().newInstance();
            thLog   = startLogger(ilogger);

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
            env.getQLog().put(env.ENDS);
            thLog.join();

        } catch (InterruptedException ex) {
            System.err.println(ex.getLocalizedMessage());
        } catch(Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
            System.out.println(ex);
        }
    }
    // Arranca el consumidor de logs
    public Thread startLogger(IMTLogger ilogger) {

        Thread log = new Thread(new MTLoggerCons(env, ilogger));
        log.start();
        QLogger.setQueue(env.getQLog());
        return log;
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
            value = props.getProperty("appName");
            if (value != null) env.setAppName(value);
        } catch (NumberFormatException ex) {
            Logger2.warning("Ignorado valor del atributo no numerico: " + value);
        }
    }
    private void summary() {
        logger.msg(MSG.SUMMARY, new Long(System.currentTimeMillis() - beg)
                              ,env.getNumThreads()
                              ,env.getChunk()
                              ,env.getRead());
    }
}
