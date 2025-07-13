package es.mpt.sgtic.geiser.tools.libreria.processes;

import es.mpt.sgtic.geiser.tools.libreria.base.Config;

import javax.persistence.EntityManager;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Consumidor implements Runnable {
    private PriorityBlockingQueue<Long> cola;
    private LinkedBlockingQueue<String> qlog;
    private CountDownLatch              latch;
    private Config cfg;

    public Consumidor(CountDownLatch latch) {
        this.cfg   = Config.getInstance();
        this.cola  = cfg.qdat;
        this.qlog  = cfg.qlog;
        this.latch = latch;
    }

    @Override
    public void run() {
        Migrador migrador = new Migrador();
        EntityManager em = null;
        int     cont = 0;
        boolean res  = true;
        long    beg = 0L;

        try {
            while (res) {
                try {
                    if (cont++ % cfg.chunk == 0) {
                        beg = System.currentTimeMillis();
                        // em = cfg.emf.createEntityManager();
                        em.getTransaction().begin();
                    }
                    long id = (long) cola.take();

                    if (id < 0) res = false; // Mensaje EOP
                    migrador.migra(em, id);
                    if (cont % cfg.chunk == 0) {
                        em.getTransaction().commit();
                        em.close();
                        System.out.println(String.format("COMMIT %6d-%6d-%s", cont, System.currentTimeMillis() - beg, Thread.currentThread().getName()));
                        beg = System.currentTimeMillis();
                    }
                } catch (Exception ex) {
                    err(ex, "ERROR migrando datos");
                    if (em.isJoinedToTransaction()) em.getTransaction().rollback();
                    cont = 0;
                }
            }
            System.out.println(Thread.currentThread().getName() + " - hace commit final: " + cont);
            if (em.isJoinedToTransaction()) em.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        this.latch.countDown();
    }
    private void err(Exception e, String msg) {
        String name = Thread.currentThread().getName();
        try {
            qlog.put("0;" + name + " - ERROR " + msg);
            qlog.put("0;" + name + " - " + e.getLocalizedMessage());
            if (e.getCause() != null) {
                qlog.put("0;" + name + " - " + e.getCause().getLocalizedMessage());
            }
        } catch (InterruptedException ex) {
            // throw new RuntimeException(ex);
        }
    }
}

