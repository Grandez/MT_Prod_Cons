package com.sdp.poc.threading.test.core;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MTEnv<T> {
    public  static final long   ENDT = Long.MAX_VALUE; // Acabar lo pendiente
    public  static final long   ENDI = -1l;            // Acabar lo actual
    public  static final String ENDS = String.valueOf(Long.MAX_VALUE);

    public  static final String SEP  = ":";  // Separador de valores en CLP
    public  static final String TOK  = ";";  // Separador de campos en mensajes

    int threads;
    int timeout;
    int chunk = 0;
    String appName = "MTAPP";

    long read = 0;

    CountDownLatch latch    = null;
    PriorityBlockingQueue<Long> qdat = new PriorityBlockingQueue<Long>();
    LinkedBlockingQueue<String> qlog = new LinkedBlockingQueue<String>();

    public PriorityBlockingQueue<Long> getQueue() { return qdat; }
    public LinkedBlockingQueue<String> getQLog()  { return qlog; }

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

    public String getAppName()               { return appName; }
    public void   setAppName(String appName) { this.appName = appName; }

    public Long getRead()          { return read;      }
    public void setRead(long read) { this.read = read; }
    public void read()             { this.read++;      }
}
