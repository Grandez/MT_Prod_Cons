package es.mpt.sgtic.geiser.framework.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class ProdCons<T> {
    protected ConfigFramework cfg;
    protected LinkedBlockingQueue<String> qlog;

    protected ProdCons (T type) {
        this.cfg   = ConfigFramework.getInstance(type);
        this.qlog  = cfg.qlog;
    }
}
