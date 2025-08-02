package com.sdp.poc.threading.thread.core;
/**
 * Area de comunicacion o contexto de aplicacion
 * Extiende de CABase
 */

import com.sdp.poc.threading.base.CtxBase;

public class CtxSimple extends CtxBase {
    private Integer items = 1000;

    private CtxSimple() {}
    private static class CtxSimpleInner { private static final CtxSimple INSTANCE = new CtxSimple(); }
    public  static CtxSimple getInstance() { return CtxSimpleInner.INSTANCE; }
    public  static CtxSimple getInstance(String app) {
        CtxSimpleInner.INSTANCE.setAppName(app);
        return CtxSimpleInner.INSTANCE;
    }
    public void setItems(int items) { this.items = items; }
    public int getItems()           { return items; }
}
