package es.mpt.sgtic.geiser.tools.libreria.processes;

import es.mpt.sgtic.geiser.tools.libreria.base.Config;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class Procesador extends ProcesoBase implements IProceso {

    public int run() {

        latch = new CountDownLatch(cfg.threads + 1); // el 1 es el logger
        executor = Executors.newFixedThreadPool(cfg.threads);

        try {
            //motor.run(Productor.class, Consumidor.class);
            //factory.startLogger(latch);   // LoggerCons
            //for (int i = 0; i < cfg.threads ; i++) executor.execute(factory.getConsumer(latch)); // Consumers
            executor.shutdown();          // No mas hilos
            //factory.getProducer().run();  // NO ES ASINCRONO
            for (long l = 0 ; l < cfg.threads; l++) cfg.qdat.put(Config.ENDT); // Notificar fin hilos
            cfg.qlog.put(Config.ENDS);
            latch.await();
         } catch(Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            ex.printStackTrace();
            System.out.println(ex);
        }
        return 0;
    }
}
