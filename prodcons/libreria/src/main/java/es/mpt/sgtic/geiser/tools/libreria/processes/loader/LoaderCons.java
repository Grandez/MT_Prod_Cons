package es.mpt.sgtic.geiser.tools.libreria.processes.loader;

import es.mpt.sgtic.geiser.framework.core.ConsumerBase;
import es.mpt.sgtic.geiser.framework.core.Factory;
import es.mpt.sgtic.geiser.framework.core.Logger;
import es.mpt.sgtic.geiser.framework.interfaces.IConsumer;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public class LoaderCons extends ConsumerBase implements IConsumer {
/*
try (Connection connection = DatabaseConnectionPool.getConnection()) {
        // Aquí puedes realizar la operación que deseas, por ejemplo, insertar un registro
        String sql = "INSERT INTO registros (id, mensaje) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, "Mensaje desde el hilo " + id);
            stmt.executeUpdate();
            System.out.println("Hilo " + id + " ha escrito en la base de datos");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
*/
    public LoaderCons(CountDownLatch latch) {
        super();
        this.cfg   = Config.getInstance();
        this.cola  = cfg.qdat;
        this.qlog  = cfg.qlog;
        this.latch = latch;
    }

    @Override
    public void run() {

        int    cont = 0;
        boolean res  = true;
        try {
            conn = Factory.getInstance().newConsSession();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            while (res) {
                try {
                    if (cont++ % cfg.chunk == 0) txBegin();
                    long id = cola.take();
                    if (id < 0) break;
                    cfg.item();
                    long asiento = (new Asiento()).load(cont);
                    new Interesados().load(asiento);
                    new Anexo().load(asiento);
                    new Estado().load(asiento);

                    if (cont % cfg.chunk == 0) txEnd(cont);
                } catch (Exception ex) {
                    Logger.err("ERROR Cargando datos");
                    Logger.err(ex.getLocalizedMessage());
                    if (ex.getCause() != null) Logger.err(ex.getCause().getLocalizedMessage());
                    if (em.isJoinedToTransaction()) em.getTransaction().rollback();
                    cont = 0;
                }
            }
            if (em.isJoinedToTransaction()) em.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        em.close();
        this.latch.countDown();
    }
}
