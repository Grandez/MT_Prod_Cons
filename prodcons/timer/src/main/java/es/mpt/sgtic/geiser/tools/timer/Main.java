package es.mpt.sgtic.geiser.tools.timer;

import es.mpt.sgtic.geiser.framework.core.Motor;
import es.mpt.sgtic.geiser.framework.exceptions.ProdConException;
import es.mpt.sgtic.geiser.tools.timer.base.Config;
import es.mpt.sgtic.geiser.tools.timer.base.PARMS;
import es.mpt.sgtic.geiser.tools.timer.base.Parameters;
import es.mpt.sgtic.geiser.tools.timer.threads.TimerCons;
import es.mpt.sgtic.geiser.tools.timer.threads.TimerProd;

public class Main {

    private Config cfg;

    public static void main(String[] args) {
        Main app = new Main();
        app.run(args);
        System.exit(0);
    }

    private void run(String[] args) {
        System.out.println("Ejemplo de proceso multihilo con temporizador");
        System.out.println("Cada hilo sacara un mensaje hasta que se alcance el tiempo");

        Motor motor = new Motor();
        try {
            prepareEnvironment(args);
            motor.run(TimerProd.class, TimerCons.class);
        } catch (ProdConException e) {
            throw new RuntimeException(e);
        } finally {
            cfg.printSummary();
        }

        System.exit(0);
    }

    private void prepareEnvironment(String[] args) throws ProdConException {
        cfg = Config.getInstance();
        Parameters parms = new Parameters(PARMS.values());
        parms.processArgs(args);
        cfg.manageConnections();
    }
}