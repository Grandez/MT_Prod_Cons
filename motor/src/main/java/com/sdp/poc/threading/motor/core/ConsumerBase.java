package com.sdp.poc.threading.motor.core;
/**
 * Clase base de los consumidores
 * Se encarga de gestionar la transaccionalidad segun los chunks definidos
 */

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class ConsumerBase {
    protected ConfigFramework cfg;
    protected PriorityBlockingQueue<Long> cola;
    protected LinkedBlockingQueue<String> qlog;
    protected CountDownLatch latch;
    protected EntityManager em = null;
    protected long beg;
    protected Connection conn;

    protected void txBegin() throws SQLException {

       beg = System.currentTimeMillis();

       conn.setAutoCommit(false);
//       em = cfg.emf.createEntityManager();
//       em.getTransaction().begin();
//       System.out.println("Transaccion: " + em.isJoinedToTransaction());
    }
    protected void txEnd(int cont) throws SQLException {
        conn.commit();
        conn.setAutoCommit(false);

//       em.getTransaction().commit();
//       em.close();
//       System.out.println(String.format("COMMIT %6d-%6d-%s", cont, System.currentTimeMillis() - beg, Thread.currentThread().getName()));
       beg = System.currentTimeMillis();
    }
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        Thread.currentThread().setName(proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1])));
    }
    protected String getName() { return Thread.currentThread().getName(); }
}
