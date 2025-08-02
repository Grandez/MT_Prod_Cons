package com.sdp.poc.threading.simple.core;
/**
 * Area de comunicacion o contexto de aplicacion
 * Extiende de CABase
 */

import com.sdp.poc.threading.base.config.CtxBase;

public class CtxSimple extends CtxBase {
    private Integer items = 1000;

    private CtxSimple() {}
    private static class CASimpleInner    { private static final CtxSimple INSTANCE = new CtxSimple(); }
    public  static CtxSimple getInstance() { return CtxSimple.CASimpleInner.INSTANCE; }
    public  static CtxSimple getInstance(String app) {
        CtxSimple.CASimpleInner.INSTANCE.setAppName(app);
        return CtxSimple.CASimpleInner.INSTANCE;
    }
    public void setItems(int items) { this.items = items; }
    public int getItems()           { return items; }
}
