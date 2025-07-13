package es.mpt.sgtic.geiser.tools.libreria.processes.loader;

import es.mpt.sgtic.geiser.framework.core.ConfigFramework;
import es.mpt.sgtic.geiser.framework.core.Logger;
import es.mpt.sgtic.geiser.framework.interfaces.IProducer;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import es.mpt.sgtic.geiser.tools.libreria.base.TBL;
import es.mpt.sgtic.geiser.tools.libreria.base.exceptions.LibException;
import org.hibernate.Session;

import javax.persistence.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static es.mpt.sgtic.geiser.framework.tools.Messages.DB_LASTS;

public class LoaderProd implements IProducer {
    private Config cfg = Config.getInstance();

    @Override
    public void run() {
        Logger.info("Iniciando carga para %dM asientos", cfg.registros);
        Logger.info("Procesando bloques de %d ", cfg.chunk);
        try {
            getLastIds();
            long total = cfg.registros * 1000;
            for (long cont = 0; cont < total;        cont++) {
                cfg.read();
                cfg.qdat.put(cont);
            }
            for (long l    = 0 ; l   < cfg.threads;  l++)    cfg.qdat.put(ConfigFramework.ENDT); // Notificar fin hilos
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void getLastIds() throws LibException {
        Session session = cfg.newSession();
//        Connection connx = cfg.getConnection();
        for (TBL tbl : TBL.values()) {
            try {
                String sql = "SELECT max(a.id) FROM " + tbl.table + " a";
                Query query = session.createQuery(sql);
                Object datos = query.getSingleResult();
                cfg.ids[tbl.ordinal()] = (datos == null) ? 0L : (Long) datos;
            } catch (Exception ex) {
                throw new LibException(DB_LASTS, ex);
            }
        }
        for (int i = 0; i < cfg.ids.length; i++) {
            if (cfg.ids[i] > cfg.get()) cfg.set(cfg.ids[i]);
        }
        session.close();
    }

}
