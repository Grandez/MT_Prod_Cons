package com.sdp.poc.threading.test.base;

import com.sdp.poc.threading.base.config.CABase;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class CAMT<T> extends CABase {
    public  static final long   ENDT = Long.MAX_VALUE; // Acabar lo pendiente
    public  static final long   ENDI = -1l;            // Acabar lo actual

    int threads = 1;
    int timeout = 0;
    int chunk   = 0;

    long read = 0;

    CountDownLatch latch    = null;
    PriorityBlockingQueue<Long> qdat = new PriorityBlockingQueue<Long>();

    public abstract Properties getCustomProps();

    public PriorityBlockingQueue<Long> getQueue() { return qdat; }

    public Integer  getNumThreads() { return threads; }
    public Integer  getTimeout()    { return timeout; }
    public Integer  getChunk()      { return chunk; }

    public void setNumThreads(int threads) { this.threads = threads; }
    public void setTimeout   (int timeout) { this.timeout = timeout; }
    public void setChunk     (int chunk)   { this.chunk = chunk; }

    public CountDownLatch getLatch()                     {
        if (latch == null) latch = new CountDownLatch(threads + 1);
        return latch;
    }
    public void           setLatch(int latch) { this.latch = new CountDownLatch(latch); }
}
