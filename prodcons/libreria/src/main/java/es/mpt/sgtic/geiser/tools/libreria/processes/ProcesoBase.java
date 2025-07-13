package es.mpt.sgtic.geiser.tools.libreria.processes;

import es.mpt.sgtic.geiser.framework.core.Motor;
import es.mpt.sgtic.geiser.tools.libreria.base.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public abstract class ProcesoBase {
    protected CountDownLatch  latch    = null;
    protected ExecutorService executor = null;
    protected Factory         factory = Factory.getInstance();
    protected Config          cfg = Config.getInstance();
    protected Motor           motor = new Motor();
}
