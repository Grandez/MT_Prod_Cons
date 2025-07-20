package com.sdp.poc.threading.threading.motor.core;
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

import com.sdp.poc.threading.threading.motor.interfaces.ICommarea;
import com.sdp.poc.threading.threading.motor.interfaces.IConsumer;
import com.sdp.poc.threading.threading.motor.interfaces.IProducer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Motor {
    CountDownLatch latch    = null;
    ExecutorService executor = null;
    ICommarea commarea;

    public void run(ICommarea commarea, Class prodClass, Class consClass) {
        this.commarea = commarea;

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
            for (long l = 0; l < commarea.getNumThreads(); l++) commarea.getQDat().put(commarea.ENDT);
            latch.await();

            if (commarea.getTimeout() > 0) { // Si habia timer, ha acabado antes de tiempo. Pararlo
                thTimer.interrupt();
                thTimer.join();
            }

            commarea.getQLog().put(commarea.ENDS);
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
        Logger.setQueue(commarea.getQLog());
        return log;
    }
    // Arranca el monitor de tiempo
    public Thread startTimer() {
        if (commarea.getTimeout() == 0) return null;
        Thread thr = new Thread(new Timer(Thread.currentThread()));
        thr.setName("Timer");
        thr.start();
        return thr;
    }



}
