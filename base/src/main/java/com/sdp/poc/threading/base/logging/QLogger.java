package com.sdp.poc.threading.base.logging;

import com.sdp.poc.threading.base.CtxBase;

public class QLogger extends QLoggerBase {
    private static Thread thrLog = null;

    public static void start(String app) {
        thrLog = new Thread(new QLoggerCons(app));
        thrLog.start();
    }
    public static void start() {
        thrLog = new Thread(new QLoggerCons("NONAME"));
        thrLog.start();
    }
    public static void stop() {
        // Esto se llama desde diferentes sitios
        // Controlar que ya este hecho
        if (thrLog == null) return;
        try {
            qlog.put("end:" + Long.MAX_VALUE);
            thrLog.join();
        } catch (InterruptedException e) {
            System.err.println("DEV: QLog InterruptedException");
        } finally {
            thrLog = null;
        }
    }
    public static QLoggerProd getLogger(CtxBase ca) {
       return new QLoggerProd(ca);
    }
}
