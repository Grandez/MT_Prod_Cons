/**
 * Simplemente pone los mensajes en la cola
 * si procede en funcion del tipo de mensaje y de su nivel
 */
package com.sdp.poc.threading.base.logging;

import com.sdp.poc.threading.base.CtxBase;
import com.sdp.poc.threading.base.mask.RC;

public class QLoggerProd extends QLoggerBase{
    CtxBase ctx;
    public QLoggerProd(CtxBase ca) {
        this.ctx = ca;
    }
    // Se escribe siempre
    public void msg(String code, Object ... parms) {
        writeMessage(code, parms);
    }
    public void info(int level, String code, Object ... parms) {
        if (checkLevel(MSGTYPE.NFO, level)) writeMessage(code, parms);
    }
    public void warn(int level, String code, Object ... parms) {
        ctx.rc |= RC.WARNING;
        if (checkLevel(MSGTYPE.NFO, level)) writeMessage(code, parms);
    }
    public void end(String str) {
        writeMessage(str);
    }

    private boolean checkLevel(MSGTYPE type, int level) {
        return true;
    }
    private void writeMessage(String code, Object ... parms) {
        int i;
        StringBuffer head = mountHeader(code);
        StringBuffer buff = new StringBuffer();
        buff.append(head);
        if (parms.length > 0) {
            for (i = 0; i < parms.length - 1; i++) buff.append(parms[i]).append(';');
            buff.append(parms[i]);
        }

        try {
            qlog.put(buff.toString());
        } catch (Exception e) {
            // Do nothing
            System.err.println("Dev: check");
        }

    }

    /**
     * Monta la parte de cabecera del mensaje
     * @return el buffer con la cabecera
     */
    private StringBuffer mountHeader(String code) {
        StringBuffer buff = new StringBuffer(512);
        buff.append(ctx.getAppName());
        buff.append(":").append(System.currentTimeMillis() / 1000); // epoch
        buff.append(":").append(ctx.getPid());
        buff.append(':').append(Thread.currentThread().getName());

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement item = stack[4];
        buff.append(":").append(item.getClassName());
        buff.append(":").append(item.getMethodName());
        buff.append(":").append(item.getLineNumber());
        buff.append(":").append(code).append(':');
        return buff;
    }
}
