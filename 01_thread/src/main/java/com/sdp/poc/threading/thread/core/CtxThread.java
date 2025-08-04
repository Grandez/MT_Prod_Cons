package com.sdp.poc.threading.thread.core;
/**
 * Area de comunicacion o contexto de aplicacion
 * Extiende de CABase
 */

import com.sdp.poc.threading.base.CtxBase;

public class CtxThread extends CtxBase {
    private Integer items = 1000;
    private Integer threads = 1;

    private CtxThread() {}
    private static class CtxThreadInner { private static final CtxThread INSTANCE = new CtxThread(); }
    public  static CtxThread getInstance() { return CtxThreadInner.INSTANCE; }
    public  static CtxThread getInstance(String app) {
        CtxThreadInner.INSTANCE.setAppName(app);
        return CtxThreadInner.INSTANCE;
    }
    public void setItems(int items)     { this.items = items; }
    public int getItems()               { return items; }
    public void setThreads(int threads) { this.threads = threads; }
    public int getThreads()             { return threads; }

}
