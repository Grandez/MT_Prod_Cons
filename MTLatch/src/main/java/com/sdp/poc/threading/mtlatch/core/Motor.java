package com.sdp.poc.threading.mtlatch.core;
/**
 * Motor del paradigma Porductor/Consumidor
 * Recibe las dos clases asociadas:
 *  - El productor
 *  - El Consumidor
 *
 *  Se encarga de ejecutar el sistema de acuerdo con
 *  la parametrizacion de hilos
 *
 *  Primero se inician Consumidores
 *  Luego Productor
 *  Se finaliza en orden inverso
 *
 */

import com.sdp.poc.threading.base.CtxBase;
import com.sdp.poc.threading.base.parameters.Props;
import com.sdp.poc.threading.mtlatch.interfaces.IMTConsumer;
import com.sdp.poc.threading.mtlatch.interfaces.IMTProducer;
import com.sdp.poc.threading.base.logging.QLoggerProd;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Motor {
    ExecutorService executor = null;
    CtxBase ctx;
    long    beg;
    QLoggerProd logger;

    public Motor()                          { this(null, null, null); }
    public Motor(String file)               { this(file, null, null);    }
    public Motor(String file, String label) {
        this(file, label, null);
    }
    public Motor(CtxBase ca)                { this(null,null, ca);    }

    /**
     * Constructor del motor de threading
     * @param fConfig Fichero de configuracion, si null mt.properties
     * @param label   Prefijo de las entradas de configuracion
     * @param ctx     Parametros pasados por linea de comandos en la interfaz
     */
    public Motor(String fConfig, String label, CtxBase ctx) {
        this.ctx = ctx;
        loadPropsData(Props.load(getFileProperties(fConfig), label), label);
        loadPropsData(ctx.getCommandLine(), null);
    }

    @SuppressWarnings("unchecked")
    public void run(Class prodClass, Class consClass) {
        beg = System.currentTimeMillis();
        Thread thTimer;

        int threads = ctx.getNumThreads() + 1; // Sumamos el productor
        ctx.setLatch(new CountDownLatch(threads));
        executor = Executors.newFixedThreadPool(threads);

        try {
            // Arranca los consumidores
            for (int i = 0; i < ctx.getNumThreads(); i++) {
                IMTConsumer iCons = (IMTConsumer) consClass.getConstructor().newInstance();
                MTConsumer consumer = new MTConsumer(ctx, iCons);
                executor.execute(consumer); // Consumers
            }

            // Arranca el productor
            IMTProducer iprod = (IMTProducer) prodClass.getConstructor().newInstance();
            Thread thrProd = new Thread(new MTProducer(ctx, iprod));

            // Arrancar el gestor de tiempo
            thTimer = startTimer(thrProd);

            executor.execute(thrProd);
            executor.shutdown();          // No mas hilos
            ctx.getLatch().await();       // Esperar

            // Si habia timer, ha acabado antes de tiempo. Pararlo
            if (ctx.getTimeout() > 0) {
                thTimer.interrupt();
                thTimer.join();
            }
        } catch (InterruptedException ex) {
            System.err.println(ex.getLocalizedMessage());
        } catch(Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
            System.out.println(ex);
        }
    }
    // Arranca el monitor de tiempo
    public Thread startTimer(Thread prod) {
        if (ctx.getTimeout() == 0) return null;
        Thread thr = new Thread(new Timer(prod, ctx));
        thr.start();
        return thr;
    }
    private void loadPropsData(Props props, String prfx) {
        if (props == null) return;
        String p = prfx == null ? "" : prfx + ".";
        ctx.setNumThreads(props.getInteger(p + "threads", ctx.getNumThreads()));
        ctx.setTimeout(props.getInteger(p + "timeout", ctx.getTimeout()));
        ctx.setChunk(props.getInteger(p + "chunk",   ctx.getChunk()));
    }
    private String getFileProperties (String fileProps) {
        if (fileProps == null) {
            String[] toks = getClass().getName().split("\\.");
            fileProps = toks[toks.length - 3];
        }
        if (fileProps.indexOf('.') == -1) fileProps = fileProps + ".properties";
        return fileProps;
    }
}
