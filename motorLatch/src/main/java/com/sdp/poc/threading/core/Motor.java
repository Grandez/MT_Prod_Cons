package com.sdp.poc.threading.core;
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
import com.sdp.poc.threading.config.CAMT;
import com.sdp.poc.threading.interfaces.ICommarea;
import com.sdp.poc.threading.interfaces.IMTConsumer;
import com.sdp.poc.threading.interfaces.IMTProducer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class Motor {
    CountDownLatch latch    = null;
    ExecutorService executor = null;
    CAMT ca;

    public Motor() { this(null, null, null); }
    public Motor(String file) {
        this(file, null, null);
    }
    public Motor(String file, String label) {
        this(file, label, null);
    }
    public Motor(Properties custom) {        this(null,null,custom);    }
    public Motor(String fConfig, String label, Properties custom) {
        ca = CAMT.getInstance();
        String fname = "mt.properties";
        if (fConfig != null) {
            fname = fConfig;
            if (fConfig.indexOf('.') == -1) fname = fname + ".properties";
        }
        loadPropsData(Props.load(fname), label);
        loadPropsData(custom, null);
    }

    public void run(ICommarea commarea, Class prodClass, Class consClass) {


        Thread thTimer = null;
        Thread thLog = null;

        // Sumamos el productor
        int threads = commarea.getNumThreads();
        latch = new CountDownLatch(threads);
        executor = Executors.newFixedThreadPool(threads);

        PriorityBlockingQueue<Long> q = ca.getQueue();

        try {
            thLog = startLogger();
            thTimer = startTimer();

            // Arrancamos los consumidores
            for (int i = 0; i < ca.getNumThreads(); i++) {
                IMTConsumer icons = (IMTConsumer) consClass.getConstructor().newInstance();
                MTConsumer  consumer = new MTConsumer(latch, icons);
                executor.execute(consumer); // Consumers
            }

            IMTProducer iprod = (IMTProducer) prodClass.getConstructor().newInstance();
            Thread thrProd = new Thread(new MTProducer(iprod));
            thrProd.start();
            thrProd.join();

//            IMTProducer iprod = (IMTProducer) prodClass.getConstructor().newInstance();
//            MTProducer  producer = new MTProducer(latch, iprod);
//            executor.execute(producer); // Consumers



            // Notificar que acaben
            for (long l = 0; l < ca.getNumThreads(); l++)
                q.put(CAMT.ENDT);
            executor.shutdown();          // No mas hilos
            latch.await();

            if (commarea.getTimeout() > 0) { // Si habia timer, ha acabado antes de tiempo. Pararlo
                thTimer.interrupt();
                thTimer.join();
            }

            commarea.getQLog().put(CAMT.ENDS);
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
    public Thread startLogger() {
        Thread log = new Thread(new LoggerCons());
        log.setName("Logger");
        log.start();
        //QLogger.setQueue(ca.getQLog());
        return log;
    }
    // Arranca el monitor de tiempo
    public Thread startTimer() {
        if (ca.getTimeout() == 0) return null;
        Thread thr = new Thread(new Timer("Timer", Thread.currentThread(), ca));
        thr.start();
        return thr;
    }
    private void loadPropsData(Properties props, String prfx) {
        if (props == null) return;
        String value = "";
        try {
            value = props.getProperty(prfx == null ? "threads" : prfx + ".threads");
            if (value != null) ca.setNumThreads(Integer.parseInt(value));
            value = props.getProperty(prfx == null ? "timeout" : prfx + ".timeout");
            if (value != null) ca.setTimeout(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            CLogger.warning("Ignorado valor del atributo no numerico: " + value);
        }
    }
}
