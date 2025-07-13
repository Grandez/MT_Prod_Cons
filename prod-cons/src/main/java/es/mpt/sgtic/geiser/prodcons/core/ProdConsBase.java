package es.mpt.sgtic.geiser.prodcons.core;

/*
  Clase base de productores y consumidores
  Cada uno de ellos puede usar un em o emf diferente
 */

import es.mpt.sgtic.geiser.prodcons.beans.ResultThread;
import es.mpt.sgtic.geiser.prodcons.interfaces.IProdConsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public abstract class ProdConsBase {
    protected IProdConsConfig cfg;
    protected String label;

    @Autowired
    @Qualifier("txManagerThreads")
    protected PlatformTransactionManager txManager;
    @Autowired
    @Qualifier("entityManagerFactoryGeiser")
    protected EntityManagerFactory emf;

    protected EntityManager em;
    protected EntityTransaction tx = null;
    protected DefaultTransactionDefinition defTx;
    protected ResultThread result = new ResultThread();

//    TransactionStatus txStatus;

    public    void setLabel(String label) { this.label = label; }
    protected void setThreadName () { setThreadName(this.getClass().getSimpleName()); }
    protected void setThreadName (String proceso) {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        Thread.currentThread().setName(proceso + "-" + String.format("%03d", Integer.parseInt(toks[toks.length - 1])));
    }
    protected String getThreadName() { return Thread.currentThread().getName(); }
    protected int    getThreadId() {
        String tname = Thread.currentThread().getName();
        String[] toks = tname.split("-");
        try {
            return Integer.parseInt(toks[toks.length - 1]);
        } catch (NumberFormatException ex) {
            return (new Random()).nextInt(9);
        }
    }
    protected void print(String msg) {
        SimpleDateFormat simpleformat = new SimpleDateFormat("HH:mm:ss ");
        System.out.println(simpleformat.format(new Date()) + getThreadName() + " - " + msg);
    }
    protected void txBegin() {
        // em.getTransaction().begin();
        tx.begin();
//         if (cfg.chunk > 1)
//        txStatus = txManager.getTransaction(defTx);
//        em = emf.createEntityManager();
//        tx = em.getTransaction();
//        tx.begin();
//        if (!em.getTransaction().isActive()) {
//            em.getTransaction().begin();
            print("Empieza transaccion");
//        }
        // em.getTransaction().begin();
    }

    protected void txEnd() {
        try {
          //if (tx.isActive()) txManager.commit(txStatus);
            if (tx.isActive()) {
                tx.commit();
            }
//        if (em.getTransaction().isActive()) {
//            em.getTransaction().commit();
            print("Hace commit");
//        }

        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            throw ex;
        }
    }

}
