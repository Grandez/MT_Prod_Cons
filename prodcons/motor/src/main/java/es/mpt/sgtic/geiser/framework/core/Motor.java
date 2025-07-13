package es.mpt.sgtic.geiser.framework.core;
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

import es.mpt.sgtic.geiser.framework.interfaces.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Motor {
    CountDownLatch latch    = null;
    ExecutorService executor = null;

    public void run(Class prodClass, Class consClass) {
        Thread thTimer = null;
        Thread thLog = null;

        ConfigFramework cfg = ConfigFramework.getInstance();
        latch = new CountDownLatch(cfg.threads);
        executor = Executors.newFixedThreadPool(cfg.threads);

        try {
            thLog = startLogger();
            if (cfg.timer > 0) thTimer = startTimer();

            // Arrancamos los consumidores
            for (int i = 0; i < cfg.threads; i++) {
                IConsumer cons = (IConsumer) consClass.getConstructor(CountDownLatch.class).newInstance(latch);
                executor.execute(cons); // Consumers
            }
            executor.shutdown();          // No mas hilos

            // Se inicia el productor en este hilo
            IProducer prod = (IProducer) prodClass.newInstance();
            prod.run();

//            if (cfg.timer > 0) thTimer = startTimer((Thread) prod);
            // Notificar que acaben
            for (long l = 0; l < cfg.threads; l++) cfg.qdat.put(ConfigFramework.ENDT); // Notificar fin hilos
            latch.await();

            if (cfg.timer > 0) { // Si habia timer, ha acabado antes de tiempo. Pararlo
                thTimer.interrupt();
                thTimer.join();
            }

            cfg.qlog.put(ConfigFramework.ENDS);
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
        Logger.setQueue(ConfigFramework.getInstance().qlog);
        return log;
    }
    // Arranca el monitor de tiempo
    public Thread startTimer() {
        Thread thr = new Thread(new Timer(Thread.currentThread()));
        thr.setName("Timer");
        thr.start();
        return thr;
    }



}
