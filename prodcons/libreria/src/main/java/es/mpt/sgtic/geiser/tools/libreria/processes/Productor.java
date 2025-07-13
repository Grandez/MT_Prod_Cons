package es.mpt.sgtic.geiser.tools.libreria.processes;
/**
 * Responsable de escribir en la cola los id de los asientos
 * a procesar
 * Al final del proceso escribe los mensajes de EOP
 *
 */

import es.mpt.sgtic.geiser.framework.interfaces.IProducer;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;

import javax.persistence.EntityManager;

public class Productor extends Thread implements IProducer {
    private Config cfg;

    public Productor() {
        this.cfg   = Config.getInstance();
    }
    public void run() {
        EntityManager     em      = null;
        ScrollableResults csr     = null;
        Session           session = null;
        int               cont    = 0;

        try {
            em = cfg.emf.createEntityManager();
            session = em.unwrap(Session.class);
            csr = session.createQuery("SELECT a.id FROM AsientoOld a")
                         .setReadOnly(true)
                         .setCacheable(false)
                         .scroll(ScrollMode.FORWARD_ONLY);

            while (csr.next()) {
                cont++;
                if (cont > 10) break;
                Long id = (Long) csr.getLong(0);
                cfg.qdat.put(id);
            }
        } catch (Exception e) {
            try {
                cfg.qlog.put("0;" + "ERROR en lectura de datos");
                cfg.qlog.put("0;" + e.getLocalizedMessage());
                if (e.getCause() != null) cfg.qlog.put("0;" + e.getCause().getLocalizedMessage());
            } catch (InterruptedException ex) {
                // Do nothing
            }
        } finally {
          if (csr     != null)                     csr.close();
          if (session != null && session.isOpen()) session.close();
          if (em      != null && em.isOpen())      em.close();
        }
    }
}
