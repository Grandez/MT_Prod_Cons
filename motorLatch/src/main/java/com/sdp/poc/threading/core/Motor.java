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
import com.sdp.poc.threading.base.msg.Logger2;
import com.sdp.poc.threading.base.msg.QLogger;
import com.sdp.poc.threading.config.Commarea;
import com.sdp.poc.threading.interfaces.ICommarea;
import com.sdp.poc.threading.interfaces.IConsumer;
import com.sdp.poc.threading.interfaces.IProducer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Motor {
    CountDownLatch latch    = null;
    ExecutorService executor = null;
    ICommarea commarea;
    Commarea MT;
    String propsPreffix = null;

    public Motor() {}
    public Motor(String label) {
        this.propsPreffix = label;
    }
    public void run(ICommarea commarea, Class prodClass, Class consClass) {
        initCommarea(commarea);
        MT = (Commarea) commarea;

        Thread thTimer = null;
        Thread thLog = null;

        int threads = commarea.getNumThreads();
        latch = new CountDownLatch(threads);
        executor = Executors.newFixedThreadPool(threads);

        try {
            thLog = startLogger();
            thTimer = startTimer();

            // Arrancamos los consumidores
            for (int i = 0; i < commarea.getNumThreads(); i++) {
                IConsumer cons = (IConsumer) consClass.getConstructor(CountDownLatch.class).newInstance(latch);
                executor.execute(cons); // Consumers
            }
            executor.shutdown();          // No mas hilos

            // Se inicia el productor en este hilo
            IProducer prod = (IProducer) prodClass.newInstance();
            prod.run();

            // Notificar que acaben
            for (long l = 0; l < commarea.getNumThreads(); l++) commarea.getQDat().put(MT.ENDT);
            latch.await();

            if (commarea.getTimeout() > 0) { // Si habia timer, ha acabado antes de tiempo. Pararlo
                thTimer.interrupt();
                thTimer.join();
            }

            commarea.getQLog().put(MT.ENDS);
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
        QLogger.setQueue(commarea.getQLog());
        return log;
    }
    // Arranca el monitor de tiempo
    public Thread startTimer() {
        if (commarea.getTimeout() == 0) return null;
        Thread thr = new Thread(new Timer(Thread.currentThread(), (Commarea) commarea));
        thr.setName("Timer");
        thr.start();
        return thr;
    }

    private void initCommarea(ICommarea comm) {
        this.commarea = comm;
        loadPropsData(Props.load("mt.properties"), "propsPreffix");
        loadPropsData(commarea.getCustomProps(), null);
    }
    private void loadPropsData(Properties props, String prfx) {
        String value = "";
        try {
            value = props.getProperty(propsPreffix == null ? "threads" : propsPreffix + ".threads");
            if (value != null) commarea.setNumThreads(Integer.parseInt(value));
            value = props.getProperty(propsPreffix == null ? "timeout" : propsPreffix + ".timeout");
            if (value != null) commarea.setTimeout(Integer.parseInt(value));
        } catch (NumberFormatException ex) {
            Logger2.warning("Ignorado valor del atributo no numerico: " + value);
        }
    }
}
