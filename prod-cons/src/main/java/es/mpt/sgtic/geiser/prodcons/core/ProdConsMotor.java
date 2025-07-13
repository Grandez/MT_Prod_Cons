package es.mpt.sgtic.geiser.prodcons.core;
/**
 * Motor del paradigma Productor/Consumidor
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

// import es.mpt.sgtic.geiser.corecomun.entorno.entity.TbcgeiConfiguracionEntorno;
import es.mpt.sgtic.geiser.prodcons.beans.*;
import es.mpt.sgtic.geiser.prodcons.config.ProdConsConfig;
import es.mpt.sgtic.geiser.prodcons.interfaces.IProdConsConfig;
import es.mpt.sgtic.geiser.prodcons.logging.QLogger;
import es.mpt.sgtic.geiser.prodcons.logging.QLoggerCons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
// public class Motor extends LockProceso {
public class ProdConsMotor extends ProdConsBase {

    @Autowired
    private ApplicationContext context;
    ExecutorService executor = null;
    IProdConsConfig cfg;

    /**
     * Gestiona el modelo Productor/Consumidor
     * En esta version existe un unico productor que indica los elementos a procesar por ID
     * Los consumidores van obteniendo esos ID de manera exclusiva y procesandolos
     *
     * Los hilos son fijos en esta version
     *
     * El productor se ejecuta en este hilo
     * El proceso acaba cuando:
     *   - Se han procesado los mensajes
     *   - Ha transcurrido cierto tiempo (ProdConsTimer)
     *
     * @param productor Clase que produce las tareas
     * @param consumer Clase que realiza la tarea para un elemento
     */
    public void start(Callable<ResultThread> productor, Callable<ResultThread> consumer, String label) {
        List<Future<ResultThread>> futures = new ArrayList<>();

        ResultGlobal result = new ResultGlobal();

        Thread thTimer = null;
        Thread thLog = null;

        cfg = ProdConsConfig.getConfig(label);

        QLogger.info("Inicio tarea (start)");

        updateConfigFromTable();

//      Para runnables con execute
//      cfg.latch = new CountDownLatch(cfg.threads);
        executor = Executors.newFixedThreadPool(cfg.getThreads());
        try {
            thLog = startLogger(label);
            thTimer = startTimer(cfg);

            // Version con latch y execute
//          for (int i = 0; i < cfg.threads; i++) executor.execute(consumer);  // Enviar tarea y obtener el Future
            for (int i = 0; i < cfg.getThreads(); i++) { //futures.add(executor.submit(consumer));
                Future<ResultThread> future = executor.submit(consumer);  // Enviar tarea y obtener el Future
                futures.add(future);  // Guardar el Future para luego esperar su finalización
            }

            ResultThread producidos = productor.call();
            result.add(producidos);

            // Notificar que acaben
            for (long l = 0; l < cfg.getThreads(); l++) cfg.getQueue().put(ProdConsConfig.END_NORMAL); // Notificar fin hilos

            System.out.println("Tengo futuros: " + futures.size());
/*
            for (Future<ResultThread> future : futures) {
                // Imprimir el estado
                if (future.isDone()) {
                    System.out.println("Tarea completada: " + future.get());
                } else if (future.isCancelled()) {
                    System.out.println("Tarea cancelada." + future.toString());
                } else {
                    System.out.println("Tarea en progreso..." + future.toString());
                }
            }
*/
            // Esperar por Submit
            for (Future<?> future : futures) {
                try {
                    ResultThread res = (ResultThread) future.get();
                    result.add(res);  // Bloquear el hilo hasta que la tarea termine
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    printSummary();
                }
            }

            // Esperar por execute
//           cfg.latch.await();
            if (thTimer != null) { // Si habia timer, ha acabado antes de tiempo. Pararlo
                thTimer.interrupt();
                thTimer.join();
            }

            cfg.getQLog().put(ProdConsConfig.END_NORMAL);
            thLog.join();

            System.out.println("Acaba start");
        } catch (InterruptedException ex) {
            System.err.println(ex.getLocalizedMessage());
        } catch(Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
            System.out.println(ex);
        }
        QLogger.info("Fin tarea (start)");
    }

    // Arranca el consumidor de logs
    public Thread startLogger(String label) {
        Thread log = new Thread(new QLoggerCons(label));
        log.setName("Logger");
        log.start();
        QLogger.setQueue(cfg.getQLog());
        return log;
    }
    // Arranca el monitor de tiempo
    public Thread startTimer(IProdConsConfig cfg) {
        if (cfg.getTimer() <= 0) return null;
        Thread thr = new Thread(new ProdConsTimer(Thread.currentThread(), cfg));
        thr.setName("ProdConsTimer");
        thr.start();
        return thr;
    }

    /**
     * En un entorno multihilo, tarde o temprano, se producira
     * algun problema temporal en tiempo de ejecucion:
     * - Demasiada carga puntual
     * - Desajustes en la evolucion del sistema
     * - dimensionamiento poco optimo
     * - etc.
     *
     * Para esos casos basta con modificar la configuracion de la tabla
     * TBCGEI_CONFIGURACIONENOTORNO dinamicamente para la tarea implicada
     * y esta se ajustara dinamicamente en la proxima ejecucion
     *
     * Logicamente, si el ajuste es temporal habra que volver a poner la
     * configuracion original.
     * Si es permanente es recomendable actualizar el fichero de propiedades
     * que sera el que normalmente se consulte para saber la configuracion
     *
     */
    public void updateConfigFromTable() {
/* PEND
        String sql = "SELECT c FROM TbcgeiConfiguracionEntorno c WHERE c.nombreBean = :label";
        String label = cfg.getIdTabla();
        if (label == null) return;

        try {
                em = emf.createEntityManager();
//            EntityManager em = context.getBean(EntityManager.class);
            Query qry = em.createQuery(sql);
            qry.setParameter("label", label);
            List<TbcgeiConfiguracionEntorno> records = qry.getResultList();
            for (TbcgeiConfiguracionEntorno record : records) {
                String clave = record.getPropiedadBean();
                String valor = record.getValor();
                if (clave.compareToIgnoreCase(ProdConsConfig.LOTE)  == 0) cfg.setLote(valor);
                if (clave.compareToIgnoreCase(ProdConsConfig.CHUNK) == 0) cfg.setChunk(valor);
                if (clave.compareToIgnoreCase(ProdConsConfig.HILOS) == 0) cfg.setHilos(valor);
            }
        } catch (Exception ex) {
            QLogger.exception(ex);
        }

 */
    }
    private void printSummary() {
       SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");

       String str = String.format("CSV,%s,lote: %d,leidos: %d,escritos: %d,avisos: %d,tiempo: %s",
                                  sdf.format(System.currentTimeMillis()),
                                  cfg.getLote(), result.getRead(), result.getWrite(),
                                  result.getWarnings(),result.getElapsed());
       QLogger.resumen(str);

       QLogger.summary("Leidos",   result.getRead());
       QLogger.summary("Escritos", result.getWrite());
       QLogger.summary("Avisos",   result.getWarnings());
       QLogger.summary("Erroneos", result.getErrors());
       QLogger.elapsed(result.getElapsed());
    }
}
